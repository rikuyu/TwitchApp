package com.example.twitchapp.util

import com.example.twitchapp.model.data.clip_data.*
import com.example.twitchapp.model.data.stream_data.Channel
import com.example.twitchapp.model.data.stream_data.Stream
import com.example.twitchapp.model.data.stream_data.Streams
import com.example.twitchapp.model.data.stream_data.Thumbnail

object Dummy {

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

    private val dummyChannel = Channel(
        language = "dummy_language",
        logo = "dummy_logo",
        name = "dummy_name",
        url = "dummy_url"
    )

    private val dummyStream = Stream(
        id = 1,
        channel = dummyChannel,
        preview = Thumbnail(medium = "dummy_medium"),
        game = "dummy_game",
        viewers = 10
    )

    val dummyStreams = Streams(listOf(dummyStream))
}