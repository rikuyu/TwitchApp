package com.example.twitchapp.model.data.streamdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Thumbnail(
    val large: String,
    val medium: String,
    val small: String,
    val template: String
) : Parcelable