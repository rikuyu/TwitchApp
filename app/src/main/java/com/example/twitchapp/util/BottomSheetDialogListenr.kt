package com.example.twitchapp.util

import android.content.Context

interface BottomSheetDialogListenr {
    /*
    * 「再生する」をクリック
    */
    fun play(context: Context, url: String)

    /*
    * 「いいね」をクリック
    */
    fun favorite(addFavorite: () -> Unit)

    /*
    * 「削除する」をクリック
    */
    fun delete(deleteFavorite: () -> Unit)

    /*
    * 「URLをコピー」をクリック
    */
    fun copy(url: String)

    /*
    * 「閉じる」をクリック
    */
    fun back()
}