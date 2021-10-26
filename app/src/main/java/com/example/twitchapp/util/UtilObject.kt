package com.example.twitchapp.util

import android.view.View
import com.example.twitchapp.ui.MainViewModel

object UtilObject {
    fun visible(view: View) {
        view.visibility = View.VISIBLE
    }

    fun invisible(view: View) {
        view.visibility = View.INVISIBLE
    }

    fun createGameButton(view: View ,vm: MainViewModel, gameTitle: String){
        view.setOnClickListener {
            vm.fetchClip(gameTitle)
        }
    }
}