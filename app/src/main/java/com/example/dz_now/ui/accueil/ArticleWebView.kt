package com.example.dz_now.ui.accueil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import com.example.dz_now.R
import android.webkit.WebViewClient
import com.example.dz_now.entites.Article


class ArticleWebView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_web_view)

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        Log.e("test", article!!.link)
        webView.loadUrl(article!!.link)
    }

    companion object {
        var article : Article? = null
    }

}
