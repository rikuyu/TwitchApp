package com.example.twitchapp.model.data.clipdata

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Broadcaster(
    @Json(name = "channel_url") val channelUrl: String,
    val name: String
) : Parcelable