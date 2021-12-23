package com.example.twitchapp.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface ItemClickListener {
    /*
    * サムネイル画像をクリックしたとき
    */
    fun thumbnailClickListener(url: String)

    /*
    * アイテムを長押ししたとき
    */
    fun <T> longClickListener(item: T, screen: ScreenType)
}

@Parcelize
enum class ScreenType : Parcelable {
    STREAM,
    CLIP,
    FAVORITE
}