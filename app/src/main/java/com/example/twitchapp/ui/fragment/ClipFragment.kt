package com.example.twitchapp.ui.fragment

import android.content.Intent
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.R
import com.example.twitchapp.adapter.ClipAdapter
import com.example.twitchapp.databinding.FragmentClipBinding
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.MainActivity
import com.example.twitchapp.util.Resource
import kotlinx.android.synthetic.main.clip_item.view.*

class ClipFragment : Fragment(R.layout.fragment_clip) {
    private lateinit var viewModel: MainViewModel

    private lateinit var clipAdapter: ClipAdapter
    private var clipList: List<Clip>? = null

    private var _binding: FragmentClipBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 左上のゲームアイコンを円形にする
        binding.gameIcon.outlineProvider = clipOutlineProvider
        binding.gameIcon.clipToOutline = true

        viewModel = (activity as MainActivity).mainViewModel

        viewModel.clips.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { response ->
                        clipList = response.clips
                        clipAdapter = ClipAdapter(requireContext(), clipList)
                        binding.clipRecyclerView.adapter = clipAdapter

                        clipAdapter.setOnThumbnailClickListener(object :
                            ClipAdapter.ShowClip {
                            override fun showClip(view: View, position: Int) {
                                val uri = Uri.parse(clipList!![position].url)
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                startActivity(intent)
                            }
                        })
                        clipAdapter.setOnFavoIconClickListener(object:
                            ClipAdapter.HandleDatabase{
                            override fun handleDatabase(clip: Clip) {
                                if(binding.clipRecyclerView.heart_icon.isChecked){
                                    viewModel.insertClip(clip)
                                }else{
                                    viewModel.deleteClip(clip)
                                }
                            }
                            }
                        )
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

    private fun fetchGameClip() {
        binding.pubgMobile.setOnClickListener {
            viewModel.fetchPubgMobileClip()
        }

        binding.apex.setOnClickListener {
            viewModel.fetchApexClip()
        }

        binding.amongus.setOnClickListener {
            viewModel.fetchAmongusClip()
        }

        binding.genshin.setOnClickListener {
            viewModel.fetchGenshinClip()
        }

        binding.minecraft.setOnClickListener {
            viewModel.fetchMinecraftClip()
        }

        binding.fortnite.setOnClickListener {
            viewModel.fetchFortniteClip()
        }

        binding.callofduty.setOnClickListener {
            viewModel.fetchCallofdutyClip()
        }

        binding.lol.setOnClickListener {
            viewModel.fetchLolClip()
        }
    }
}