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
        _id = 1,
        broadcaster_language = "dummy_broadcaster_language",
        broadcaster_software = "dummy_broadcaster_software",
        broadcaster_type = "dummy_broadcaster_type",
        created_at = "dummy_created_at",
        description = "dummy_description",
        display_name = "dummy_display_name",
        followers = 10,
        game = "dummy_game",
        language = "dummy_language",
        logo = "dummy_logo",
        mature = false,
        name = "dummy_name",
        partner = false,
        privacy_options_enabled = false,
        private_video = false,
        profile_banner = null,
        profile_banner_background_color = "dummy_profile_banner_background_color",
        status = "dummy_status",
        updated_at = "dummy_updated_at",
        url = "dummy_url",
        video_banner = "dummy_video_banner",
        views = 1
    )

    private val dummyStream = Stream(
        _id = 1,
        average_fps = 60,
        broadcast_platform = "dummy_platform",
        channel = dummyChannel,
        community_id = "dummy_community_id",
        community_ids = listOf(1, 2, 3),
        created_at = "dummy_created_at",
        delay = 100,
        game = "dummy_game",
        is_playlist = false,
        preview = dummyThumbnail,
        stream_type = "dummy_stream_type",
        video_height = 100,
        viewers = 10
    )

    val dummyStreams = Streams(listOf(dummyStream))
}