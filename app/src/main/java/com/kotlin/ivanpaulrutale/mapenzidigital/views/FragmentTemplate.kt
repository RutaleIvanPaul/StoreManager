package com.kotlin.ivanpaulrutale.mapenzidigital.views

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.kotlin.ivanpaulrutale.mapenzidigital.R
import com.kotlin.ivanpaulrutale.mapenzidigital.adapter.CustomAdapter

class FragmentTemplate : Fragment() {
    private var page:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            page = it.getString("page")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_template_layout, container, false)

        loadContent(page,view)

        return view
    }

    private fun loadContent(page: String?,view: View) {
        val webView = view.findViewById<WebView>(R.id.web_view)
        webView.webViewClient = MyWebClient(view)
        webView.settings.javaScriptEnabled = true
        val progressBar =view.findViewById<ProgressBar>(R.id.webView_progressBar)
        when (page){
            "Home"->{
                webView.loadUrl("https://mapenzi.ug/")
            }
            "Videography"->{
                webView.loadUrl("https://mapenzi.ug/videography/")

            }
            "Photography"->{
                webView.loadUrl("https://mapenzi.ug/photography/")
            }
            "Portfolio"->{
                webView.loadUrl("https://mapenzi.ug/case-study/")
            }
            "Team"->{
                webView.loadUrl("https://mapenzi.ug/our-team/")

            }
            "Contact"->{
                webView.loadUrl("https://mapenzi.ug/contact-us/")
            }
            "Order" ->{
                val intent = Intent(this.context,OrderActivity::class.java)
                startActivity(intent)
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(page: String) =
            FragmentTemplate().apply {
                arguments = Bundle().apply {
                    putString("page", page)
                }
            }
    }


    class MyWebClient(view: View): WebViewClient() {
        val progressBar = view.findViewById<ProgressBar>(R.id.webView_progressBar)
        @Override
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        @Override
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            progressBar.visibility = View.VISIBLE
            view!!.loadUrl(request!!.url.toString())
            return true
        }

        @Override
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

    }


    }

