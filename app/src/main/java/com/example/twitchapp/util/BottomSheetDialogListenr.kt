package com.example.twitchapp.util

import android.content.Context
import com.example.twitchapp.model.data.clip_data.Clip

interface BottomSheetDialogListenr {
    /*
    * 「再生する」をクリック
    */
    fun play(context: Context, url: String)

    /*
    * 「いいね」をクリック
    */
    fun favorite(clip: Clip)

    /*
    * 「削除する」をクリック
    */
    fun delete(clip: Clip)

    /*
    * 「URLをコピー」をクリック
    */
    fun copy(url: String)

    /*
    * 「閉じる」をクリック
    */
    fun back()
}