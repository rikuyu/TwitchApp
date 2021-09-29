package com.example.twitchapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.adapter.ClipAdapter
import com.example.twitchapp.databinding.FragmentClipBinding
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClipFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var clipAdapter: ClipAdapter
    private var clipList: List<Clip>? = null

    private var _binding: FragmentClipBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.clips.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        clipList = it.clips
                        clipAdapter = ClipAdapter(requireContext(), clipList)
                        binding.clipRecyclerView.adapter = clipAdapter

                        clipAdapter.setOnThumbnailClickListener(object :
                                ClipAdapter.OnItemClickListener {
                                override fun showClip(url: String) {
                                    findNavController().navigate(
                                        ClipFragmentDirections
                                            .actionClipToTwitchPageFragment(url)
                                    )
                                }

                                override fun showProfile(url: String) {
                                    findNavController().navigate(
                                        ClipFragmentDirections
                                            .actionClipToTwitchPageFragment(url)
                                    )
                                }

                                override fun addClip(clip: Clip) {
                                    mainViewModel.insertGetClip(clip)
                                    Toast.makeText(
                                        requireContext(),
                                        "Gameに保存されました",
                                        Toast.LENGTH_SHORT
                                    ).show()
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

        fetchGameClip()

        binding.clipRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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

    private fun fetchGameClip() {
        binding.pubgMobile.setOnClickListener {
            mainViewModel.fetchClip("PUBG Mobile")
        }

        binding.apex.setOnClickListener {
            mainViewModel.fetchClip("Apex Legends")
        }

        binding.amongus.setOnClickListener {
            mainViewModel.fetchClip("Among Us")
        }

        binding.genshin.setOnClickListener {
            mainViewModel.fetchClip("Genshin Impact")
        }

        binding.minecraft.setOnClickListener {
            mainViewModel.fetchClip("Minecraft")
        }

        binding.fortnite.setOnClickListener {
            mainViewModel.fetchClip("Fortnite")
        }

        binding.callofduty.setOnClickListener {
            mainViewModel.fetchClip("Call of Duty: Warzone")
        }

        binding.lol.setOnClickListener {
            mainViewModel.fetchClip("League of Legends")
        }
    }
}