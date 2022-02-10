package com.example.twitchapp.util

import com.example.twitchapp.model.data.streamdata.Channel
import com.example.twitchapp.model.data.streamdata.Stream
import com.example.twitchapp.model.data.streamdata.Streams
import com.example.twitchapp.model.data.streamdata.Thumbnail

object DummyStream {

    private val dummyThumbnail = Thumbnail(
        large = "dummy_large",
        medium = "dummy_medium",
        small = "dummy_small",
        template = "dummy_template"
    )

    private val dummyChannel = Channel(
        language = "dummy_language",
        logo = "dummy_logo",
        name = "dummy_name",
        url = "dummy_url"
    )

    private val dummyStream = Stream(
        id = 1,
        channel = dummyChannel,
        game = "dummy_game",
        viewers = 10
    )

    val dummyStreams = Streams(listOf(dummyStream))
}