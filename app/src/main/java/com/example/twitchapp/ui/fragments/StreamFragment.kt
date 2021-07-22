package com.example.twitchapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StreamFragment : Fragment(R.layout.fragment_stream) {

    lateinit var mainViewModel: MainViewModel

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

    private var currentGameTitle = "PUBG Mobile"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = (activity as MainActivity).mainViewModel

        mainViewModel.streams.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        streamList = it.streams
                        twitchAdapter = StreamAdapter(requireContext(), streamList)
                        binding.streamRecyclerView.adapter = twitchAdapter

                        Log.d("Stream", "Success")

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
                    response.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        Log.d("Stream", message)
                    }
                }
                is Resource.Loading -> {
                    Log.d("Stream", "Loading")
                    showProgressBar()
                }
            }
        })

        fetchGameStream()

        binding.streamRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.swipeRefresh.setOnRefreshListener {
            mainViewModel.fetchStream(currentGameTitle)
            if (binding.swipeRefresh.isRefreshing) {
                binding.swipeRefresh.isRefreshing = false;
                Log.d("Stream", "更新終了")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideProgressBar() {
        binding.progressbar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun fetchGameStream() {
        binding.pubgMobile.setOnClickListener {
            mainViewModel.fetchStream("PUBG Mobile")
            currentGameTitle = "PUBG Mobile"
        }

        binding.apex.setOnClickListener {
            mainViewModel.fetchStream("Apex Legends")
            currentGameTitle = "Apex Legends"
        }

        binding.amongus.setOnClickListener {
            mainViewModel.fetchStream("Among Us")
            currentGameTitle = "Among Us"
        }

        binding.genshin.setOnClickListener {
            mainViewModel.fetchStream("Genshin Impact")
            currentGameTitle = "Genshin Impact"
        }

        binding.minecraft.setOnClickListener {
            mainViewModel.fetchStream("Minecraft")
            currentGameTitle = "Minecraft"
        }

        binding.fortnite.setOnClickListener {
            mainViewModel.fetchStream("Fortnite")
            currentGameTitle = "Fortnite"
        }

        binding.callofduty.setOnClickListener {
            mainViewModel.fetchStream("Call of Duty: Warzone")
            currentGameTitle = "Call of Duty: Warzone"
        }

        binding.lol.setOnClickListener {
            mainViewModel.fetchStream("League of Legends")
            currentGameTitle = "League of Legends"
        }
    }
}