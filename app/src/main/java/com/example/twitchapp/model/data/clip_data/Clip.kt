package com.example.twitchapp.model.data.clip_data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.twitchapp.model.data.stream_data.Thumbnail
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "clips")
data class Clip(
    @PrimaryKey
    @Json(name = "tracking_id") val trackingId: String,
    @Embedded(prefix = "broadcaster_") val broadcaster: Broadcaster,
    @Embedded(prefix = "curator_") val curator: Curator,
    @Embedded val thumbnails: Thumbnail,
    val duration: Double,
    val game: String,
    val language: String,
    val title: String,
    val url: String,
    val views: Int,
) : Parcelable