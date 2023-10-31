package ua.playappsuccess.first.presentation.customUI


import android.content.Context
import android.os.Message
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

class PaymentScreen(context: Context) : WebView(context){

    fun initialPaymentScreen(root: FrameLayout){
        webViewClient = getWVC()
        webChromeClient = getWCC(rootElement = root)
    }

    private fun getWVC(): WebViewClient{
        return object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                CookieManager.getInstance().flush()
            }
        }
    }

    private fun getWCC(rootElement: FrameLayout): WebChromeClient{
        return object : WebChromeClient(){
            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {

                val newWeb = PaymentScreen(context = context)
                newWeb.initialPaymentScreen(rootElement)
                val transport = resultMsg?.obj as WebView.WebViewTransport
                transport.webView = newWeb
                resultMsg.sendToTarget()

                return true
            }

            override fun onCloseWindow(window: WebView?) {
                super.onCloseWindow(window)
                rootElement.removeView(window)
            }
        }
    }
}