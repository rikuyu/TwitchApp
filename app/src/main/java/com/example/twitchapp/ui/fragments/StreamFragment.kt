package com.example.twitchapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Outline
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.ui.MainActivity
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.adapter.StreamAdapter
import com.example.twitchapp.databinding.FragmentStreamBinding
import com.example.twitchapp.model.data.streamdata.Stream
import com.example.twitchapp.util.Resource

class StreamFragment : Fragment(R.layout.fragment_stream) {

    lateinit var viewModel: MainViewModel

    private lateinit var twitchAdapter: StreamAdapter
    private var streamList: List<Stream>? = null

    private var _binding: FragmentStreamBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStreamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 左上のゲームアイコンを円形にする
        binding.gameIcon.outlineProvider = clipOutlineProvider
        binding.gameIcon.clipToOutline = true

//        val connectivityManager = context?.getSystemService(
//            Context.CONNECTIVITY_SERVICE
//        ) as ConnectivityManager
//
//        if (context == null) {
//            Toast.makeText(requireContext(), "aaa", Toast.LENGTH_SHORT).show()
//        }
//
//        val capabilities =
//            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//
//        if (capabilities != null) {
//            when {
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
//                    Toast.makeText(requireContext(), "Wifiに接続", Toast.LENGTH_LONG).show()
//                }
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
//                    Toast.makeText(requireContext(), "モバイル通信に接続しています", Toast.LENGTH_LONG).show()
//                }
//                else -> {
//                    Toast.makeText(requireContext(), "モバイル通信に接続しています", Toast.LENGTH_LONG).show()
//                }
//            }
//        } else {
//            Toast.makeText(requireContext(), "ネットワークに接続していません", Toast.LENGTH_LONG).show()
//        }

        viewModel = (activity as MainActivity).mainViewModel

        viewModel.streams.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        streamList = it.streams
                        twitchAdapter = StreamAdapter(requireContext(), streamList)
                        binding.streamRecyclerView.adapter = twitchAdapter

                        twitchAdapter.setOnItemClickListener(object :
                            StreamAdapter.OnItemClickListener {
                            override fun onItemClickListener(url: String) {
                                val uri = Uri.parse(url)
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                        })
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let{ message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        Log.d("Stream", message)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        fetchGameStream()

        binding.streamRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 左上のゲームアイコンを円形にする
    private val clipOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setOval(
                0,
                0,
                view.width,
                view.height
            )
        }
    }

    private fun hideProgressBar() {
        binding.progressbar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun fetchGameStream() {
        binding.pubgMobile.setOnClickListener {
            viewModel.fetchStream("PUBG Mobile")
        }

        binding.apex.setOnClickListener {
            viewModel.fetchStream("Apex Legends")
        }

        binding.amongus.setOnClickListener {
            viewModel.fetchStream("Among Us")
        }

        binding.genshin.setOnClickListener {
            viewModel.fetchStream("Genshin Impact")
        }

        binding.minecraft.setOnClickListener {
            viewModel.fetchStream("Minecraft")
        }

        binding.fortnite.setOnClickListener {
            viewModel.fetchStream("Fortnite")
        }

        binding.callofduty.setOnClickListener {
            viewModel.fetchStream("Call of Duty: Warzone")
        }

        binding.lol.setOnClickListener {
            viewModel.fetchStream("League of Legends")
        }
    }
}