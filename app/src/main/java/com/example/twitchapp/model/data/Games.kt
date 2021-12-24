package com.example.twitchapp.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Games(val title: String) : Parcelable {
    PUBG_MOBILE("PUBG Mobile"),
    APEX_LEGENDS("Apex Legends"),
    AMONG_US("Among Us"),
    GENSHIN("Genshin Impact"),
    FORTNITE("Fortnite"),
    MINECRAFT("Minecraft"),
    CALL_OF_DUTY("Call of Duty: Warzone"),
    LEAGUE_OF_LEGENDS("League of Legends"),
    ALL("all")
}