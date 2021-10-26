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
import com.example.twitchapp.R
import com.example.twitchapp.ui.adapter.ClipAdapter
import com.example.twitchapp.databinding.FragmentClipBinding
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.util.Resource
import com.example.twitchapp.util.UtilObject
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
                                    getString(R.string.clip_save),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.clip_get_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        setupTopMenu()

        binding.clipRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideProgressBar() {
        UtilObject.invisible(binding.progressbar)
    }

    private fun showProgressBar() {
        UtilObject.visible(binding.progressbar)
    }

    private fun setupTopMenu() {
        binding.apply {
            UtilObject.apply {
                createGameButton(pubgMobile, mainViewModel, PUBG_MOBILE)
                createGameButton(apex, mainViewModel, APEX_LEGENDS)
                createGameButton(amongus, mainViewModel, AMONG_US)
                createGameButton(genshin, mainViewModel, GENSHIN)
                createGameButton(minecraft, mainViewModel, MINECRAFT)
                createGameButton(fortnite, mainViewModel, FORTNITE)
                createGameButton(callofduty, mainViewModel, CALL_OF_DUTY)
                createGameButton(lol, mainViewModel, LEAGUE_OF_LEGENDS)
            }
        }
    }

    companion object {
        private const val PUBG_MOBILE = "PUBG Mobile"
        private const val APEX_LEGENDS = "Apex Legends"
        private const val AMONG_US = "Among Us"
        private const val GENSHIN = "Genshin Impact"
        private const val FORTNITE = "Fortnite"
        private const val MINECRAFT = "Minecraft"
        private const val CALL_OF_DUTY = "Call of Duty: Warzone"
        private const val LEAGUE_OF_LEGENDS = "League of Legends"
    }
}