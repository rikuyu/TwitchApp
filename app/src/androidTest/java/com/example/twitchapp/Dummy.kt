package com.example.twitchapp

import com.example.twitchapp.model.data.clipdata.*

object Dummy {

    private val dummyBroadcaster = Broadcaster(
        channel_url = "dummy_url",
        display_name = null,
        id = null,
        logo = null,
        name = "dummy_broadcaster_name"
    )

    private val dummyVod = Vod(id = "dummy_id", url = null)
    private val dummyCurator = Curator(
        channel_url = null,
        display_name = null,
        id = null,
        logo = null,
        name = "dummy_name"
    )
    private val dummyThumbnails = Thumbnails(
        medium = "dummy_thumbnail",
        small = null,
        tiny = null
    )

    val dummyClip = Clip(
        tracking_id = "dummy_clip_tracking_id",
        broadcaster = dummyBroadcaster,
        created_at = "dummy_string",
        curator = dummyCurator,
        duration = 10.0,
        embed_html = "dummy_string",
        embed_url = "dummy_string",
        game = "dummy_string",
        language = "dummy_string",
        slug = "dummy_string",
        thumbnails = dummyThumbnails,
        title = "dummy_string",
        url = "dummy_string",
        views = 10,
        vod = dummyVod
    )
}