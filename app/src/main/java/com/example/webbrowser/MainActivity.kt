package com.example.webbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.webbrowser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.urlEditText.setText(url)
                }
            }
        }
        binding.webView.loadUrl("https://www.google.com")
        binding.urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.webView.loadUrl(binding.urlEditText.text.toString())
                true
            } else {
                false
            }
        }
        registerForContextMenu(binding.webView)
    }
    override fun onBackPressed() {
        if (binding.webView.canGoBack()){
            binding.webView.goBack()
        } else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_google, R.id.action_Home -> {
                binding.webView.loadUrl("https://www.google.com")
                return true
            }
            R.id.action_naver, R.id.action_Home -> {
                binding.webView.loadUrl("https://www.naver.com")
                return true
            }
            R.id.action_daum, R.id.action_Home -> {
                binding.webView.loadUrl("https://www.daum.com")
                return true
            }
            R.id.action_call, R.id.action_Home -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:053-1234-5678")
                if(intent.resolveActivity(packageManager)!=null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text, R.id.action_Home -> {
                binding.webView.url?.let { url -> sendSMS("053-123-4567",url)
                }
                return true
            }
            R.id.action_email, R.id.action_Home -> {
                binding.webView.url?.let { url -> email("test@test.com","good sitet",url)
                }
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_share -> { binding.webView.url?.let{ url -> share(url)}
                return true}
            R.id.action_browser -> {binding.webView.url?.let{ url -> browser(url)}
                return true}
            }
        return super.onContextItemSelected(item)
    }
}