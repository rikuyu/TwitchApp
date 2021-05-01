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
    fun toBroadcaster(name: String): Broadcaster {
        return Broadcaster(name = name)
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
    fun toThumbnails(name: String): Thumbnails {
        return Thumbnails()
    }

    @TypeConverter
    fun fromVod(vod: Vod?): String? {
        return vod?.id
    }

    @TypeConverter
    fun toVod(url: String?): Vod {
        return Vod()
    }
}