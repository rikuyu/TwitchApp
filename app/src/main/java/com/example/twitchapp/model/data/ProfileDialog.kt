package com.example.twitchapp.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileDialog(
    val name: String,
    val avatarImageUri: String
): Parcelable
