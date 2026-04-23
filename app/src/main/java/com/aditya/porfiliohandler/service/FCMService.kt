package com.aditya.porfiliohandler.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.aditya.porfiliohandler.R
import com.aditya.porfiliohandler.presenter.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCMService"
        private const val CHANNEL_ID = "portfolio_messages"
        private const val CHANNEL_NAME = "Portfolio Messages"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New FCM token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "FCM message received — data: ${remoteMessage.data}")

        val title = remoteMessage.notification?.title ?: "New Message"
        val body = remoteMessage.notification?.body ?: ""

        val messageId = remoteMessage.data["messageId"] ?: ""

        showNotification(title, body, messageId)
    }

    private fun showNotification(title: String, body: String, messageId: String) {
        Log.d(TAG, "Showing notification — Title: $title, Body: $body")

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notifications for new portfolio contact messages"
            enableVibration(true)
        }
        manager.createNotificationChannel(channel)

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("navigate_to", "messages")
            putExtra("message_id", messageId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val notificationId = messageId.hashCode().takeIf { it != 0 } ?: System.currentTimeMillis().toInt()

        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with a proper monochrome icon later
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

        manager.notify(notificationId, notification)
    }
}