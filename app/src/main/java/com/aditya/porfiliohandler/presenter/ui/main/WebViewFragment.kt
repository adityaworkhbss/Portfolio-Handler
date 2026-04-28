package com.aditya.porfiliohandler.presenter.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.aditya.porfiliohandler.R
import com.aditya.porfiliohandler.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {

    private val webViewUrl = "https://adityaguptamobdev.vercel.app/"

    private lateinit var _binding : FragmentWebViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        setWebView()
        return _binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setWebView(){
        val webView = _binding.webview

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }

        webView.webChromeClient = WebChromeClient()

        webView.loadUrl(webViewUrl)
    }

}