package com.example.twitchapp.model.repository

import com.example.twitchapp.model.RetrofitInstance
import com.example.twitchapp.model.data.Streams
import retrofit2.Response

class TwitchRepository {
    suspend fun fetchPubgMobileStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("PUBG Mobile")
    }

    suspend fun fetchApexStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Apex Legends")
    }

    suspend fun fetchAmongusStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Among Us")
    }

    suspend fun fetchGenshinStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Genshin Impact")
    }

    suspend fun fetchMinecratStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Minecraft")
    }

    suspend fun fetchFortniteStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Fortnite")
    }

    suspend fun fetchCallofdutyStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("Call of Duty: Warzone")
    }

    suspend fun fetchLolStream(): Response<Streams>{
        return RetrofitInstance.streamApi.fetchStream("League of Legends")
    }

    fun createGameList(): MutableList<String>{
        var gameList = mutableListOf<String>()

        val reqUrl = "https://static-cdn.jtvnw.net//ttv-boxart/"

        val width = 110
        val height = 130

        val pubgmobile = "${reqUrl}PUBG%20Mobile-${width}x${height}.jpg"
        val apex = "${reqUrl}Apex%20Legends-${width}x${height}.jpg"
        val amongus = "${reqUrl}Among%20Us-${width}x${height}.jpg"
        val genshin = "${reqUrl}Genshin%20Impact-${width}x${height}.jpg"
        val minecraft = "${reqUrl}Minecraft-${width}x${height}.jpg"
        val fortnite = "${reqUrl}Fortnite-${width}x${height}.jpg"
        val callofduty = "${reqUrl}./Call%20of%20Duty:%20Warzone-${width}x${height}.jpg"
        val lol = "${reqUrl}League%20of%20Legends-${width}x${height}.jpg"
        val valorant = "${reqUrl}VALORANT-${width}x${height}.jpg"
        val overwatch = "${reqUrl}Overwatch-${width}x${height}.jpg"
        val monhan = "${reqUrl}Monster%20Hunter%20Rise-${width}x${height}.jpg"
        val mario = "${reqUrl}Super%20Mario%20Maker%202-${width}x${height}.jpg"
        val animal = "${reqUrl}Animal%20Crossing%3A%20New%20Horizons-${width}x${height}.jpg"
        val smash = "${reqUrl}Super%20Smash%20Bros.%20Ultimate-${width}x${height}.jpg"
        val grand = "${reqUrl}Grand%20Theft%20Auto%20V-${width}x${height}.jpg"

        gameList.add(pubgmobile)
        gameList.add(apex)
        gameList.add(amongus)
        gameList.add(genshin)
        gameList.add(minecraft)
        gameList.add(fortnite)
        gameList.add(callofduty)
        gameList.add(lol)
        gameList.add(valorant)
        gameList.add(overwatch)
        gameList.add(monhan)
        gameList.add(mario)
        gameList.add(animal)
        gameList.add(smash)
        gameList.add(grand)

        return gameList
    }
}