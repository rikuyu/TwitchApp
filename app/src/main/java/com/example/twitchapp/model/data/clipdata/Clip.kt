package com.example.twitchapp.model.data.clipdata

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "clips")
data class Clip(
    @PrimaryKey val tracking_id: String,
    @Embedded(prefix = "broadcaster_")
    val broadcaster: Broadcaster,
    val created_at: String,
    @Embedded(prefix = "curator_")
    val curator: Curator,
    val duration: Double,
    val embed_html: String,
    val embed_url: String,
    val game: String,
    val language: String,
    val slug: String,
    @Embedded val thumbnails: Thumbnails,
    val title: String,
    val url: String,
    val views: Int,
    @Embedded(prefix = "vod_")
    val vod: Vod? = null
) : Parcelable