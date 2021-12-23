package com.example.twitchapp.model.data.clipdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vod(
    val id: String? = null,
    val url: String? = null
) : Parcelable