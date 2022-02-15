package com.example.twitchapp.util

import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("progressbarVisibility")
    fun <T> changeProgressbarVisibility(view: ProgressBar, state: Status<T>?) {
        view.isVisible = state is Status.Loading
    }

    @JvmStatic
    @BindingAdapter("favoListVisibility")
    fun <T> changeFavoListVisibility(view: RecyclerView, state: Status<T>?) {
        view.isVisible = state is Status.Loading
    }
}