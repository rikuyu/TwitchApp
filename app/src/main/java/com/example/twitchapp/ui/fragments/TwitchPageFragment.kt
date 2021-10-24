package com.example.twitchapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.twitchapp.databinding.FragmentTwitchPageBinding
import com.example.twitchapp.util.UtilObject

class TwitchPageFragment : Fragment() {

    private val args: TwitchPageFragmentArgs by navArgs()

    private var _binding: FragmentTwitchPageBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTwitchPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startWebView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun startWebView() {
        binding.webView.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    UtilObject.visible(binding.progressbar)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    UtilObject.invisible(binding.progressbar)
                }
            }
            loadUrl(args.url)
        }
    }
}