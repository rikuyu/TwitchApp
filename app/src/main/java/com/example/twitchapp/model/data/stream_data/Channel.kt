package com.example.twitchapp.model.data.stream_data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Channel(
    val language: String,
    val logo: String,
    val name: String,
    val url: String
) : Parcelable