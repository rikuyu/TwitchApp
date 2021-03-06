package com.example.twitchapp.model.data.streamdata

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Stream(
    val _id: Long,
    val average_fps: Int,
    val broadcast_platform: String,
    val channel: Channel,
    val community_id: String,
    val community_ids: List<Int>,
    val created_at: String,
    val delay: Int,
    val game: String,
    val is_playlist: Boolean,
    val preview: Thumbnail,
    val stream_type: String,
    val video_height: Int,
    val viewers: Int
) : Parcelable