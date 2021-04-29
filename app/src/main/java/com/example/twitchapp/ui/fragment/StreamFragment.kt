package com.example.twitchapp.ui.fragment

import android.content.Intent
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.ui.MainActivity
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.adapter.TwitchAdapter
import com.example.twitchapp.databinding.FragmentStreamBinding
import com.example.twitchapp.model.data.Stream
import com.example.twitchapp.util.Resource

class StreamFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    private lateinit var twitchAdapter: TwitchAdapter
    private var streamList: List<Stream>? = null

    private var _binding: FragmentStreamBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStreamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 左上のゲームアイコンを円形にする
        binding.gameIcon.outlineProvider = clipOutlineProvider
        binding.gameIcon.clipToOutline = true

        viewModel = (activity as MainActivity).mainViewModel

        viewModel.streams.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { response ->
                        streamList = response.streams
                        twitchAdapter = TwitchAdapter(requireContext(), streamList)
                        binding.streamRecyclerView.adapter = twitchAdapter

                        twitchAdapter.setOnItemClickListener(object :
                            TwitchAdapter.OnItemClickListener {
                            override fun onItemClickListener(view: View, position: Int) {
                                val uri = Uri.parse(streamList!![position].channel.url)
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                        })
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "通信エラー", Toast.LENGTH_LONG).show()
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
//        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
//        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun fetchGameStream() {
        binding.pubgMobile.setOnClickListener {
            viewModel.fetchPubgMobileStream()
        }

        binding.apex.setOnClickListener {
            viewModel.fetchApexStream()
        }

        binding.amongus.setOnClickListener {
            viewModel.fetchAmongusStream()
        }

        binding.genshin.setOnClickListener {
            viewModel.fetchGenshinStream()
        }

        binding.minecraft.setOnClickListener {
            viewModel.fetchMinecraftStream()
        }

        binding.fortnite.setOnClickListener {
            viewModel.fetchFortniteStream()
        }

        binding.callofduty.setOnClickListener {
            viewModel.fetchCallofdutyStream()
        }

        binding.lol.setOnClickListener {
            viewModel.fetchLolStream()
        }
    }
}