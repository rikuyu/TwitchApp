package com.example.twitchapp

import android.R
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

class CircleOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
            0,
            0,
            view.getWidth(),
            view.getHeight(),
            R.attr.radius.toFloat()
        )
        view.setClipToOutline(true)
    }
}