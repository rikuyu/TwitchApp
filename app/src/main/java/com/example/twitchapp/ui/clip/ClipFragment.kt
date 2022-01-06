package com.example.twitchapp.ui.clip

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.databinding.FragmentClipBinding
import com.example.twitchapp.model.data.Games
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.CustomBottomSheetDialog
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.ui.ScreenType
import com.example.twitchapp.util.*
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClipFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val chromeCustomTabsManager: ChromeCustomTabsManager = ChromeCustomTabsManager()
    private val sharedPreferencesManager: SharedPreferencesManager = SharedPreferencesManager()

    private lateinit var clipAdapter: ClipAdapter
    private lateinit var clipItemClickListener: ClipAdapter.ClipItemClickListener

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
            val gameTitle = sharedPreferencesManager.getClipFetchGame(it)
            if (gameTitle != null) {
                loadClipFetchGame(gameTitle)
                mainViewModel.fetchClip(gameTitle)
            } else {
                switchCardViewBorder(binding.gameTitlesTopbar.pubgMobileCard, it)
                mainViewModel.fetchClip(Games.PUBG_MOBILE.title)
            }

            clipItemClickListener = object : ClipAdapter.ClipItemClickListener {
                override fun thumbnailClickListener(url: String) {
                    chromeCustomTabsManager.openChromeCustomTabs(it, url)
                }

                override fun <T> longClickListener(item: T, screen: ScreenType) {
                    setFragmentResult(
                        CUSTOM_DIALOG_KEY,
                        bundleOf(ITEM_KEY to item, SCREEN_KEY to screen)
                    )
                    CustomBottomSheetDialog(
                        mainViewModel::insertGetClip,
                        mainViewModel::deleteClip
                    ).show(parentFragmentManager, "")
                }

                override fun <T> menuClickListener(item: T, screen: ScreenType) {
                    setFragmentResult(
                        CUSTOM_DIALOG_KEY,
                        bundleOf(ITEM_KEY to item, SCREEN_KEY to screen)
                    )
                    CustomBottomSheetDialog(
                        mainViewModel::insertGetClip,
                        mainViewModel::deleteClip
                    ).show(parentFragmentManager, "")
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

            clipAdapter = ClipAdapter(it, clipItemClickListener)
            binding.clipRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = clipAdapter
            }
        }

        mainViewModel.clips.observe(
            viewLifecycleOwner,
            { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { res ->
                            res.clips.let { list ->
                                clipAdapter.submitList(list)
                            }
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
            createGameButton(pubgMobile, mainViewModel, Games.PUBG_MOBILE)
            createGameButton(apex, mainViewModel, Games.APEX_LEGENDS)
            createGameButton(amongus, mainViewModel, Games.AMONG_US)
            createGameButton(genshin, mainViewModel, Games.GENSHIN)
            createGameButton(minecraft, mainViewModel, Games.MINECRAFT)
            createGameButton(fortnite, mainViewModel, Games.FORTNITE)
            createGameButton(callofduty, mainViewModel, Games.CALL_OF_DUTY)
            createGameButton(lol, mainViewModel, Games.LEAGUE_OF_LEGENDS)
        }
    }

    private fun createGameButton(image: ImageView, vm: MainViewModel, game: Games) {
        image.setOnClickListener {
            resetGameFrameColor()
            context?.let {
                sharedPreferencesManager.saveClipFetchGame(it, game.title)
                binding.fetchGameIcon.setImageDrawable(getGameImage(it, game.title))
                binding.gameTitlesTopbar.apply {
                    when (game) {
                        Games.PUBG_MOBILE -> switchCardViewBorder(pubgMobileCard, it)
                        Games.APEX_LEGENDS -> switchCardViewBorder(apexCard, it)
                        Games.AMONG_US -> switchCardViewBorder(amongusCard, it)
                        Games.GENSHIN -> switchCardViewBorder(genshinCard, it)
                        Games.FORTNITE -> switchCardViewBorder(fortniteCard, it)
                        Games.MINECRAFT -> switchCardViewBorder(minecraftCard, it)
                        Games.CALL_OF_DUTY -> switchCardViewBorder(callofdutyCard, it)
                        Games.LEAGUE_OF_LEGENDS -> switchCardViewBorder(lolCard, it)
                        Games.ALL -> return@apply
                    }
                }
            }
            vm.fetchClip(game.title)
        }
    }

    private fun resetGameFrameColor() {
        binding.gameTitlesTopbar.apply {
            pubgMobileCard.strokeWidth = 0
            apexCard.strokeWidth = 0
            amongusCard.strokeWidth = 0
            genshinCard.strokeWidth = 0
            minecraftCard.strokeWidth = 0
            fortniteCard.strokeWidth = 0
            callofdutyCard.strokeWidth = 0
            lolCard.strokeWidth = 0
        }
    }

    private fun switchCardViewBorder(card: MaterialCardView, context: Context) {
        card.apply {
            strokeColor = ContextCompat.getColor(context, R.color.teal_200)
            strokeWidth = 8
        }
    }

    private fun loadClipFetchGame(gameTitle: String) {
        binding.apply {
            context?.let {
                fetchGameIcon.setImageDrawable(getGameImage(it, gameTitle))
                when (gameTitle) {
                    Games.PUBG_MOBILE.title -> switchCardViewBorder(gameTitlesTopbar.pubgMobileCard, it)
                    Games.APEX_LEGENDS.title -> switchCardViewBorder(gameTitlesTopbar.apexCard, it)
                    Games.AMONG_US.title -> switchCardViewBorder(gameTitlesTopbar.amongusCard, it)
                    Games.GENSHIN.title -> switchCardViewBorder(gameTitlesTopbar.genshinCard, it)
                    Games.FORTNITE.title -> switchCardViewBorder(gameTitlesTopbar.fortniteCard, it)
                    Games.MINECRAFT.title -> switchCardViewBorder(gameTitlesTopbar.minecraftCard, it)
                    Games.CALL_OF_DUTY.title -> switchCardViewBorder(gameTitlesTopbar.callofdutyCard, it)
                    Games.LEAGUE_OF_LEGENDS.title -> switchCardViewBorder(gameTitlesTopbar.lolCard, it)
                }
            }
        }
    }

    companion object {
        private const val CUSTOM_DIALOG_KEY = "custom_bottom_dialog_key"
        private const val ITEM_KEY = "item_key"
        private const val SCREEN_KEY = "screen_type"
    }
}