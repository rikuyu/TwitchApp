package com.example.twitchapp.util

import android.view.View

object UtilObject {
    fun visible(view: View) {
        view.visibility = View.VISIBLE
    }

    fun invisible(view: View) {
        view.visibility = View.INVISIBLE
    }
}