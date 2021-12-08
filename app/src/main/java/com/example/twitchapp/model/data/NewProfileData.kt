package com.example.twitchapp.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewProfileData(
    val newProfileName: String,
    val newProfileImage: String
) : Parcelable