package com.example.twitchapp.ui.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.twitchapp.R
import com.example.twitchapp.databinding.FilterDialogBinding
import com.example.twitchapp.model.data.Games

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterDialog : DialogFragment() {

    private var _binding: FilterDialogBinding? = null
    private var game: Games = Games.ALL

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

        binding.apply {
            activity?.let {
                pubgMobile.setOnClickListener {
                    resetGameFrameColor()
                    pubgMobileCard.apply {
                        strokeColor = ContextCompat.getColor(it.context, R.color.teal_200)
                        strokeWidth = 6
                    }
                    game = Games.PUBG_MOBILE
                }
                apex.setOnClickListener {
                    resetGameFrameColor()
                    apexCard.apply {
                        strokeColor = ContextCompat.getColor(it.context, R.color.teal_200)
                        strokeWidth = 6
                    }
                    game = Games.APEX_LEGENDS
                }
                amongus.setOnClickListener {
                    resetGameFrameColor()
                    amongusCard.apply {
                        strokeColor = ContextCompat.getColor(it.context, R.color.teal_200)
                        strokeWidth = 6
                    }

                    game = Games.AMONG_US
                }
                genshin.setOnClickListener {
                    resetGameFrameColor()
                    genshinCard.apply {
                        strokeColor = ContextCompat.getColor(it.context, R.color.teal_200)
                        strokeWidth = 6
                    }
                    game = Games.GENSHIN
                }
                minecraft.setOnClickListener {
                    resetGameFrameColor()
                    minecraftCard.apply {
                        strokeColor = ContextCompat.getColor(it.context, R.color.teal_200)
                        strokeWidth = 6
                    }
                    game = Games.MINECRAFT
                }
                fortnite.setOnClickListener {
                    resetGameFrameColor()
                    fortniteCard.apply {
                        strokeColor = ContextCompat.getColor(it.context, R.color.teal_200)
                        strokeWidth = 6
                    }
                    game = Games.FORTNITE
                }
                callofduty.setOnClickListener {
                    resetGameFrameColor()
                    callofdutyCard.apply {
                        strokeColor = ContextCompat.getColor(it.context, R.color.teal_200)
                        strokeWidth = 6
                    }
                    game = Games.CALL_OF_DUTY
                }
                lol.setOnClickListener {
                    resetGameFrameColor()
                    lolCard.apply {
                        strokeColor = ContextCompat.getColor(it.context, R.color.teal_200)
                        strokeWidth = 6
                    }
                    game = Games.LEAGUE_OF_LEGENDS
                }
                allGame.setOnClickListener {
                    resetGameFrameColor()
                    allGameCard.apply {
                        strokeColor = ContextCompat.getColor(it.context, R.color.teal_200)
                        strokeWidth = 6
                    }
                    game = Games.ALL
                }
                btnOk.setOnClickListener {
                    setFragmentResult(FILTER_KEY, bundleOf(GAME_KEY to game))
                    dismiss()
                }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FILTER_KEY = "filter_key"
        private const val GAME_KEY = "game_key"
    }
}