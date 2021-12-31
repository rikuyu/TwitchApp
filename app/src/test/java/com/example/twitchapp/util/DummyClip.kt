package com.example.twitchapp.util

import com.example.twitchapp.model.data.clipdata.*

object DummyClip {

    private val dummyBroadcaster = Broadcaster(
        channel_url = "dummy_url",
        display_name = null,
        id = null,
        logo = null,
        name = null
    )

    private val dummyVod = Vod(id = "dummy_id", url = null)

    private val dummyCurator = Curator(
        channel_url = "dummy_channel_url",
        display_name = "dummy_display_name",
        id = "dummy_id",
        logo = "dummy_logo",
        name = "dummy_name"
    )

    private val dummyThumbnails = Thumbnails(
        medium = "dummy_medium",
        small = "dummy_small",
        tiny = "dummy_tiny"
    )

    val dummyClip = Clip(
        tracking_id = "dummy_id_1",
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

    val dummyClipResponse = ClipResponse(
        _cursor = "_cursor",
        clips = listOf(dummyClip)
    )
}