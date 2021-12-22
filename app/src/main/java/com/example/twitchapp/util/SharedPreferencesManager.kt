package com.example.twitchapp.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager {

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            SharedPreferencesKey.SHARED_PPREFERENCE_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun getProfileName(context: Context): String? {
        return getSharedPreferences(context).getString(
            SharedPreferencesKey.STORED_PROFILE_NAME,
            null
        )
    }

    fun getProfileImage(context: Context): String? {
        return getSharedPreferences(context).getString(
            SharedPreferencesKey.STORED_PROFILE_IMAGE_URI,
            null
        )
    }

    fun saveProfileName(context: Context, name: String) {
        getSharedPreferences(context).edit()
            .putString(SharedPreferencesKey.STORED_PROFILE_NAME, name).apply()
    }

    fun saveProfileImageUrl(context: Context, url: String) {
        getSharedPreferences(context).edit()
            .putString(SharedPreferencesKey.STORED_PROFILE_IMAGE_URI, url).apply()
    }
}