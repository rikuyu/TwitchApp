package com.example.twitchapp.model.data.clipdata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Curator(
    val channel_url: String? = null,
    val display_name: String? = null,
    val id: String? = null,
    val logo: String? = null,
    val name: String? = null
) : Parcelable