package com.example.twitchapp.model

data class Channel(
    val _id: Int,
    val broadcaster_language: String,
    val broadcaster_software: String,
    val broadcaster_type: String,
    val created_at: String,
    val description: String,
    val display_name: String,
    val followers: Int,
    val game: String,
    val language: String,
    val logo: String,
    val mature: Boolean,
    val name: String,
    val partner: Boolean,
    val privacy_options_enabled: Boolean,
    val private_video: Boolean,
    val profile_banner: String,
    val profile_banner_background_color: String,
    val status: String,
    val updated_at: String,
    val url: String,
    val video_banner: String,
    val views: Int
)