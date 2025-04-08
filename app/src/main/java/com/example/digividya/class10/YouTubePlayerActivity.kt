package com.example.digividya.class10

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.digividya.R

class YouTubePlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContentView(R.layout.activity_youtube_player)

        // Get the YouTube video ID from the intent
        val videoId = intent.getStringExtra("VIDEO_ID") ?: return

        // Find the WebView
        val youtubeWebView: WebView = findViewById(R.id.youtube_webview)

        // Set up WebView settings
        val webSettings = youtubeWebView.settings
        webSettings.javaScriptEnabled = true

        // Load YouTube video in WebView using the IFrame embed URL
        val videoUrl = "https://www.youtube.com/embed/$videoId"
        youtubeWebView.loadUrl(videoUrl)

        // Ensure links inside WebView open within the WebView
        youtubeWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }


    }
}
