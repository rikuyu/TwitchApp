package com.example.twitchapp.db

import androidx.room.TypeConverter
import com.example.twitchapp.model.data.clipdata.Broadcaster
import com.example.twitchapp.model.data.clipdata.Curator
import com.example.twitchapp.model.data.clipdata.Thumbnails
import com.example.twitchapp.model.data.clipdata.Vod

class Converters {

    @TypeConverter
    fun fromBroadcaster(broadcaster: Broadcaster): String? {
        return broadcaster.name
    }

    @TypeConverter
    fun toBroadcaster(url: String): Broadcaster {
        return Broadcaster(channel_url = url)
    }

    @TypeConverter
    fun fromCurator(curator: Curator): String? {
        return curator.name
    }

    @TypeConverter
    fun toCurator(name: String): Curator {
        return Curator(name = name)
    }

    @TypeConverter
    fun fromThumbnails(thumbnails: Thumbnails): String? {
        return thumbnails.medium
    }

    @TypeConverter
    fun toThumbnails(medium: String): Thumbnails {
        return Thumbnails(medium = medium)
    }

    @TypeConverter
    fun fromVod(vod: Vod?): String? {
        return vod?.id
    }

    @TypeConverter
    fun toVod(id: String?): Vod {
        return Vod(id = id)
    }
}