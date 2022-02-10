package com.example.twitchapp.util

import com.example.twitchapp.model.data.clipdata.*
import com.example.twitchapp.model.data.streamdata.Thumbnail

object DummyClip {

    private val dummyBroadcaster = Broadcaster(
        channelUrl = "dummy_url",
        name = "dummy_name"
    )

    val dummyClip = Clip(
        trackingId = "dummy_id_1",
        broadcaster = dummyBroadcaster,
        curator = Curator(name = "dummy_name"),
        duration = 10.0,
        game = "dummy_string",
        language = "dummy_string",
        thumbnails = Thumbnail(medium = "dummy_medium"),
        title = "dummy_string",
        url = "dummy_string",
        views = 10,
    )

    val dummyClipResponse = ClipResponse(clips = listOf(dummyClip))
}