package com.example.twitchapp.util

import android.view.View
import com.example.twitchapp.ui.MainViewModel

object UtilObject {
    fun createGameButton(view: View, vm: MainViewModel, gameTitle: String) {
        view.setOnClickListener {
            vm.fetchClip(gameTitle)
        }
    }
}