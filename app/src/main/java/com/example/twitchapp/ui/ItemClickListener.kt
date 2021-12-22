package com.example.twitchapp.ui

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

enum class ScreenType {
    STREAM,
    CLIP,
    FAVORITE
}