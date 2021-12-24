package com.example.twitchapp.ui.myprofile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.twitchapp.R
import com.example.twitchapp.databinding.FilterDialogBinding
import com.example.twitchapp.model.data.Games
import com.example.twitchapp.ui.MainViewModel
import com.google.android.material.card.MaterialCardView

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterDialog : DialogFragment() {

    private var _binding: FilterDialogBinding? = null

    private val mainViewModel: MainViewModel by activityViewModels()
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterDialogBinding.inflate(requireActivity().layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.filterGame.observe(viewLifecycleOwner, { filterGame ->
            context?.let {
                when (filterGame) {
                    Games.PUBG_MOBILE -> {
                        resetGameFrameColor()
                        switchCardViewBorder(binding.pubgMobileCard, it)
                    }
                    Games.APEX_LEGENDS -> {
                        resetGameFrameColor()
                        switchCardViewBorder(binding.apexCard, it)
                    }
                    Games.AMONG_US -> {
                        resetGameFrameColor()
                        switchCardViewBorder(binding.amongusCard, it)
                    }
                    Games.GENSHIN -> {
                        resetGameFrameColor()
                        switchCardViewBorder(binding.genshinCard, it)
                    }
                    Games.FORTNITE -> {
                        resetGameFrameColor()
                        switchCardViewBorder(binding.fortniteCard, it)
                    }
                    Games.MINECRAFT -> {
                        resetGameFrameColor()
                        switchCardViewBorder(binding.minecraftCard, it)
                    }
                    Games.CALL_OF_DUTY -> {
                        resetGameFrameColor()
                        switchCardViewBorder(binding.callofdutyCard, it)
                    }
                    Games.LEAGUE_OF_LEGENDS -> {
                        resetGameFrameColor()
                        switchCardViewBorder(binding.lolCard, it)
                    }
                    Games.ALL -> {
                        resetGameFrameColor()
                        switchCardViewBorder(binding.allGameCard, it)
                    }
                    null -> {
                        resetGameFrameColor()
                        switchCardViewBorder(binding.allGameCard, it)
                    }
                }
            }
        })

        binding.apply {
            context?.let {
                setListener(pubgMobile, pubgMobileCard, Games.PUBG_MOBILE, it)
                setListener(apex, apexCard, Games.APEX_LEGENDS, it)
                setListener(amongus, amongusCard, Games.AMONG_US, it)
                setListener(genshin, genshinCard, Games.GENSHIN, it)
                setListener(minecraft, minecraftCard, Games.MINECRAFT, it)
                setListener(fortnite, fortniteCard, Games.FORTNITE, it)
                setListener(callofduty, callofdutyCard, Games.CALL_OF_DUTY, it)
                setListener(lol, lolCard, Games.LEAGUE_OF_LEGENDS, it)
                setListener(allGame, allGameCard, Games.ALL, it)
                btnOk.setOnClickListener { dismiss() }
            }
        }
    }

    private fun resetGameFrameColor() {
        binding.apply {
            pubgMobileCard.strokeWidth = 0
            apexCard.strokeWidth = 0
            amongusCard.strokeWidth = 0
            genshinCard.strokeWidth = 0
            minecraftCard.strokeWidth = 0
            fortniteCard.strokeWidth = 0
            callofdutyCard.strokeWidth = 0
            lolCard.strokeWidth = 0
            allGameCard.strokeWidth = 0
        }
    }

    private fun switchCardViewBorder(card: MaterialCardView, context: Context) {
        card.apply {
            strokeColor = ContextCompat.getColor(context, R.color.teal_200)
            strokeWidth = 7
        }
    }

    private fun setListener(
        imageView: ImageView,
        card: MaterialCardView,
        games: Games,
        context: Context
    ) {
        imageView.setOnClickListener {
            resetGameFrameColor()
            switchCardViewBorder(card, context)
            mainViewModel.filterGame.value = games
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}