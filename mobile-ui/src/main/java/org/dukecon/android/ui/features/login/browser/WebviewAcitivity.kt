package org.dukecon.android.ui.features.login.browser

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.dukecon.android.ui.R
import org.dukecon.android.ui.features.main.MainActivity

class WebviewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val url = getIntent().getStringExtra(EXTRA_URL)
        val webView = findViewById(R.id.webview) as WebView
        webView.webViewClient = CodeSniffingWebViewClient()
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        setTitle(url)
        webView.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class CodeSniffingWebViewClient : WebViewClient() {


        override fun onPageFinished(view: WebView, url: String) {
            val uri = Uri.parse(url)
            val code = uri.getQueryParameter("code")
            if (code.isNotBlank()) {
                sendUri(uri)
            } else {
                finish()
            }
        }
    }

    private fun sendUri(uri: Uri?) {
        val i = Intent(this, MainActivity::class.java)
        i.setData(uri)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(i)
    }

    companion object {
        val EXTRA_URL = "extra.url"
    }
}