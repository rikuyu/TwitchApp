package com.example.twitchapp.model.data.clipdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Thumbnails(
    val medium: String? = null,
    val small: String? = null,
    val tiny: String? = null
) : Parcelable