package com.aditya.porfiliohandler.data.datasource

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

class CloudinaryDataSource(private val context: Context) {

    private val cloudName   = "dt41uo599"
    private val uploadPreset = "portfolio_unsigned"

    suspend fun uploadImage(uri: Uri, folder: String = "portfolio/avatars"): String =
        upload(uri, "image", folder)

    suspend fun uploadPdf(uri: Uri, folder: String = "portfolio/resume"): String =
        upload(uri, "image", folder)

    private suspend fun upload(uri: Uri, resourceType: String, folder: String): String =
        withContext(Dispatchers.IO) {
            val boundary = "----FormBoundary${UUID.randomUUID()}"
            val apiUrl   = "https://api.cloudinary.com/v1_1/$cloudName/$resourceType/upload"

            val connection = (URL(apiUrl).openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                doOutput      = true
                setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")
            }

            val fileBytes = context.contentResolver.openInputStream(uri)?.readBytes()
                ?: throw IllegalStateException("Cannot open input stream for URI: $uri")

            val mimeType  = context.contentResolver.getType(uri) ?: "application/octet-stream"
            val fileName  = uri.lastPathSegment ?: "upload"

            DataOutputStream(connection.outputStream).use { dos ->

                dos.writeField(boundary, "upload_preset", uploadPreset)

                dos.writeField(boundary, "folder", folder)

                dos.writeBytes("--$boundary\r\n")
                dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"$fileName\"\r\n")
                dos.writeBytes("Content-Type: $mimeType\r\n\r\n")
                dos.write(fileBytes)
                dos.writeBytes("\r\n")

                dos.writeBytes("--$boundary--\r\n")
                dos.flush()
            }

            val responseCode = connection.responseCode
            val responseBody = if (responseCode in 200..299) {
                connection.inputStream.bufferedReader().readText()
            } else {
                val error = connection.errorStream?.bufferedReader()?.readText() ?: "Unknown error"
                throw RuntimeException("Cloudinary upload failed ($responseCode): $error")
            }

            val json = JSONObject(responseBody)
            json.getString("secure_url")
        }

    private fun DataOutputStream.writeField(boundary: String, name: String, value: String) {
        writeBytes("--$boundary\r\n")
        writeBytes("Content-Disposition: form-data; name=\"$name\"\r\n\r\n")
        writeBytes("$value\r\n")
    }
}
