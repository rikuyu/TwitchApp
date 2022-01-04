package com.example.twitchapp.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.example.twitchapp.model.data.Games.*
import com.example.twitchapp.R
import com.example.twitchapp.model.data.clipdata.Clip

object UtilObject {

    fun getGameImage(context: Context, gameTitle: String): Drawable? {
        return when (gameTitle) {
            PUBG_MOBILE.title -> ContextCompat.getDrawable(context, R.drawable.pubg_mobile)
            AMONG_US.title -> ContextCompat.getDrawable(context, R.drawable.among)
            APEX_LEGENDS.title -> ContextCompat.getDrawable(context, R.drawable.apex)
            GENSHIN.title -> ContextCompat.getDrawable(context, R.drawable.genshin)
            FORTNITE.title -> ContextCompat.getDrawable(context, R.drawable.fortnite)
            MINECRAFT.title -> ContextCompat.getDrawable(context, R.drawable.minecraft)
            CALL_OF_DUTY.title -> ContextCompat.getDrawable(context, R.drawable.callofduty)
            LEAGUE_OF_LEGENDS.title -> ContextCompat.getDrawable(context, R.drawable.lol)
            ALL.title -> ContextCompat.getDrawable(context, R.drawable.game_icon)
            else -> ContextCompat.getDrawable(context, R.drawable.ic_no_game)
        }
    }

    // Clipに100分以上の動画はない
    fun convertClipTime(time: Double): String {
        val min = time.toInt() / 60
        val sec = time.toInt() % 60

        return if (min < 10 && sec > 10) {
            "0$min:$sec"
        } else if (min < 10 && sec < 10) {
            "0$min:0$sec"
        } else if (min > 10 && sec < 10) {
            "$min:0$sec"
        } else {
            "$min:$sec"
        }
    }

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Clip>() {
        override fun areItemsTheSame(oldItem: Clip, newItem: Clip) =
            oldItem.broadcaster == newItem.broadcaster

        override fun areContentsTheSame(oldItem: Clip, newItem: Clip) =
            oldItem == newItem
    }

    fun getBitmapOrNull(contentResolver: ContentResolver, uri: Uri): Bitmap? {
        return kotlin.runCatching {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
        }.getOrNull()
    }

    fun decodeBitmapFromBase64(base64Text: String): Bitmap? {
        val bytes = Base64.decode(base64Text, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}