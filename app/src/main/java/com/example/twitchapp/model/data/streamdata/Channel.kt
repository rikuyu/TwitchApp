package com.example.twitchapp.model.data.streamdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Channel(
    val language: String,
    val logo: String,
    val name: String,
    val url: String
) : Parcelable