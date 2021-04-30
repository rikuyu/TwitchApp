package com.example.twitchapp.model.data.clipdata

data class ClipItem(
    val broadcaster: Broadcaster,
    val created_at: String,
    val curator: Curator,
    val duration: Double,
    val embed_html: String,
    val embed_url: String,
    val game: String,
    val language: String,
    val slug: String,
    val thumbnails: Thumbnails,
    val title: String,
    val tracking_id: String,
    val url: String,
    val views: Int,
    val vod: Vod? = null
)