package com.example.twitchapp.util

import com.example.twitchapp.model.data.streamdata.Channel
import com.example.twitchapp.model.data.streamdata.Stream
import com.example.twitchapp.model.data.streamdata.Streams
import com.example.twitchapp.model.data.streamdata.Thumbnail

object DummyStream {

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