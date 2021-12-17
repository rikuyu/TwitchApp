package com.example.twitchapp.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.example.twitchapp.R

class ChromeCustomTabsManager {

    fun openChromeCustomTabs(context: Context, url: String) {
        val params: CustomTabColorSchemeParams = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(context, R.color.black))
            .setNavigationBarColor(ContextCompat.getColor(context, R.color.black))
            .build()

        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_LIGHT, params)
            .setShowTitle(true)

        val intentBuilder: CustomTabsIntent = builder.build()

        val uri = Uri.parse(url)
        intentBuilder.launchUrl(context, uri)
    }
}