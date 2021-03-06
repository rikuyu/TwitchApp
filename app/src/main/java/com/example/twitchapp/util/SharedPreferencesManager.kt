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

    fun getFilterGame(context: Context): String? {
        return getSharedPreferences(context).getString(
            SharedPreferencesKey.STORED_FILTER_GAME,
            null
        )
    }

    fun getClipFetchGame(context: Context): String? {
        return getSharedPreferences(context).getString(
            SharedPreferencesKey.STORED_CLIP_FETCH_GAME,
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

    fun saveFilterGame(context: Context, game: String) {
        getSharedPreferences(context).edit()
            .putString(SharedPreferencesKey.STORED_FILTER_GAME, game).apply()
    }

    fun saveClipFetchGame(context: Context, game: String) {
        getSharedPreferences(context).edit()
            .putString(SharedPreferencesKey.STORED_CLIP_FETCH_GAME, game).apply()
    }
}