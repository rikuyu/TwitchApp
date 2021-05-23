package com.example.twitchapp.db

import androidx.room.TypeConverter
import com.example.twitchapp.model.data.clipdata.Broadcaster
import com.example.twitchapp.model.data.clipdata.Curator
import com.example.twitchapp.model.data.clipdata.Thumbnails
import com.example.twitchapp.model.data.clipdata.Vod
import com.google.gson.Gson

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
        return Thumbnails(medium = name)
    }

    @TypeConverter
    fun fromVod(vod: Vod?): String? {
        return vod?.id
    }

    @TypeConverter
    fun toVod(url: String?): Vod {
        return Vod()
    }

//    companion object {
//
//        @JvmStatic
//        @TypeConverter
//        fun fromBroadcaster(broadcaster: Broadcaster) = Gson().toJson(broadcaster)
//
//
//        @JvmStatic
//        @TypeConverter
//        fun toBroadcaster(broadcasterJson: String?) =
//            Gson().fromJson(broadcasterJson, Broadcaster::class.java)
//
//
//        @JvmStatic
//        @TypeConverter
//        fun fromCurator(curator: Curator) = Gson().toJson(curator)
//
//
//        @JvmStatic
//        @TypeConverter
//        fun toCurator(curatorJson: String?) = Gson().fromJson(curatorJson, Curator::class.java)
//
//
//        @JvmStatic
//        @TypeConverter
//        fun fromThumbnails(thumbnails: Thumbnails) = Gson().toJson(thumbnails)
//
//
//        @JvmStatic
//        @TypeConverter
//        fun toThumbnails(thumbnailsJson: String?) =
//            Gson().fromJson(thumbnailsJson, Thumbnails::class.java)
//
//
//        @JvmStatic
//        @TypeConverter
//        fun fromVod(vod: Vod?) = Gson().toJson(vod)
//
//        @JvmStatic
//        @TypeConverter
//        fun toVod(vodJson: String?) = Gson().fromJson(vodJson, Vod::class.java)
//
//    }
}