package com.example.twitchapp.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.example.twitchapp.model.data.Games.*
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.R

object UtilObject {
    fun createGameButton(view: View, vm: MainViewModel, gameTitle: String) {
        view.setOnClickListener {
            vm.fetchClip(gameTitle)
        }
    }

    fun getGameImage(context: Context, gameTitle: String): Drawable? {
        return when (gameTitle) {
            PUBG_MOBILE.title -> ContextCompat.getDrawable(context, R.drawable.pubg_mobile)
            AMONG_US.title -> ContextCompat.getDrawable(context, R.drawable.among)
            APEX_LEGENDS.title -> ContextCompat.getDrawable(context, R.drawable.apex)
            GENSHIN.title -> ContextCompat.getDrawable(context, R.drawable.genshin)
            FORTNITE.title -> ContextCompat.getDrawable(context, R.drawable.fortnite)
            MINECRAFT.title -> ContextCompat.getDrawable(context, R.drawable.minecraft)
            CALL_OF_DUTY.title -> ContextCompat.getDrawable(context, R.drawable.callofduty)
            LEAGUE_OF_LEGENDS.title -> ContextCompat.getDrawable(context, R.drawable.lol)
            else -> ContextCompat.getDrawable(context, R.drawable.ic_no_game)
        }
    }
}