package com.example.twitchapp.ui.clip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.databinding.FragmentClipBinding
import com.example.twitchapp.model.data.Games
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.util.ChromeCustomTabsManager
import com.example.twitchapp.util.Resource
import com.example.twitchapp.util.UtilObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClipFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val chromeCustomTabsManager: ChromeCustomTabsManager = ChromeCustomTabsManager()

    private lateinit var clipAdapter: ClipAdapter
    private lateinit var listenr: ClipAdapter.ClipItemClickListener

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

        context?.let {
            listenr = object : ClipAdapter.ClipItemClickListener {
                override fun thumbnailClickListener(url: String) {
                    chromeCustomTabsManager.openChromeCustomTabs(it, url)
                }

                override fun userProfileClickListener(url: String) {
                    chromeCustomTabsManager.openChromeCustomTabs(it, url)
                }

                override fun favoriteIconClickListener(clip: Clip) {
                    mainViewModel.insertGetClip(clip)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.clip_save),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        mainViewModel.clips.observe(
            viewLifecycleOwner,
            { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            clipList = it.clips
                            clipAdapter = ClipAdapter(requireContext(), clipList, listenr)
                            binding.clipRecyclerView.adapter = clipAdapter
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
            }
        )

        setupTopMenu()

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

    private fun setupTopMenu() {
        binding.gameTitlesTopbar.apply {
            UtilObject.apply {
                createGameButton(pubgMobile, mainViewModel, Games.PUBG_MOBILE.title)
                createGameButton(apex, mainViewModel, Games.APEX_LEGENDS.title)
                createGameButton(amongus, mainViewModel, Games.AMONG_US.title)
                createGameButton(genshin, mainViewModel, Games.GENSHIN.title)
                createGameButton(minecraft, mainViewModel, Games.MINECRAFT.title)
                createGameButton(fortnite, mainViewModel, Games.FORTNITE.title)
                createGameButton(callofduty, mainViewModel, Games.CALL_OF_DUTY.title)
                createGameButton(lol, mainViewModel, Games.LEAGUE_OF_LEGENDS.title)
            }
        }
    }
}