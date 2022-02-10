package com.example.twitchapp.util

import com.example.twitchapp.model.data.clipdata.*

object DummyClip {

    private val dummyBroadcaster = Broadcaster(
        channelUrl = "dummy_url",
        name = "dummy_name"
    )

    private val dummyCurator = Curator(name = "dummy_name")

    private val dummyThumbnails = Thumbnails(
        medium = "dummy_medium",
        small = "dummy_small",
        tiny = "dummy_tiny"
    )

    val dummyClip = Clip(
        trackingId = "dummy_id_1",
        broadcaster = dummyBroadcaster,
        curator = dummyCurator,
        duration = 10.0,
        game = "dummy_string",
        language = "dummy_string",
        thumbnails = dummyThumbnails,
        title = "dummy_string",
        url = "dummy_string",
        views = 10,
    )

    val dummyClipResponse = ClipResponse(clips = listOf(dummyClip))
}