package com.example.twitchapp.model.data.streamdata

import android.os.Parcelable
import com.squareup.moshi.Json

import kotlinx.parcelize.Parcelize

@Parcelize
data class Stream(
    @Json(name = "_id") val id: Long,
    val channel: Channel,
    val preview: Thumbnail,
    val game: String,
    val viewers: Int
) : Parcelable