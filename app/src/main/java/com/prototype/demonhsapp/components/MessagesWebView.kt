package com.prototype.demonhsapp.components

import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MessagesWebView(){

    val context = LocalContext.current

    val URL by remember { mutableStateOf("https://nhsapp-native-prototype-d94f8d65c0f8.herokuapp.com/pages/messages-p9") }

    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val webView = remember {
        android.webkit.WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            settings.javaScriptEnabled = true
            webViewClient = android.webkit.WebViewClient()

            //Load web view
            loadUrl(URL)

        }
    }

    DisposableEffect(webView) {
        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (webView.canGoBack()){
                    webView.goBack()
                }
            }
        }
        onBackPressedDispatcher?.addCallback(callback)

        onDispose { callback.remove() }
    }

    // The view
    AndroidView(
        factory = {webView},
        modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
fun MessagesWebViewUI(){
    Column {
        WebView()
    }
}