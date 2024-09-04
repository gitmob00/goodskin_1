package com.example.final_1.contentsList

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.final_1.R

class ContentShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content_show)

        val geturl = intent.getStringArrayExtra("url")

        val webview :WebView = findViewById(R.id.webview)
        webview.loadUrl(geturl.toString())
    }
}