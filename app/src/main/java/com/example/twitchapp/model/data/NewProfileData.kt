package com.example.twitchapp.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewProfileData(
    val newProfileName: String,
    val newProfileImage: String
) : Parcelable