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
import com.example.twitchapp.R
import com.example.twitchapp.model.data.Games
import com.example.twitchapp.model.data.clipdata.Clip

fun getGameImage(context: Context, gameTitle: String): Drawable? {
    return when (gameTitle) {
        Games.PUBG_MOBILE.title -> ContextCompat.getDrawable(context, R.drawable.pubg_mobile)
        Games.AMONG_US.title -> ContextCompat.getDrawable(context, R.drawable.among)
        Games.APEX_LEGENDS.title -> ContextCompat.getDrawable(context, R.drawable.apex)
        Games.GENSHIN.title -> ContextCompat.getDrawable(context, R.drawable.genshin)
        Games.FORTNITE.title -> ContextCompat.getDrawable(context, R.drawable.fortnite)
        Games.MINECRAFT.title -> ContextCompat.getDrawable(context, R.drawable.minecraft)
        Games.CALL_OF_DUTY.title -> ContextCompat.getDrawable(context, R.drawable.callofduty)
        Games.LEAGUE_OF_LEGENDS.title -> ContextCompat.getDrawable(context, R.drawable.lol)
        Games.ALL.title -> ContextCompat.getDrawable(context, R.drawable.game_icon)
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

fun Uri.getBitmapOrNull(contentResolver: ContentResolver): Bitmap? {
    return kotlin.runCatching {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val source = ImageDecoder.createSource(contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, this)
        }
    }.getOrNull()
}

fun decodeBitmapFromBase64(base64Text: String): Bitmap? {
    val bytes = Base64.decode(base64Text, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

object DIFF_CALLBACK : DiffUtil.ItemCallback<Clip>() {
    override fun areItemsTheSame(oldItem: Clip, newItem: Clip) =
        oldItem.broadcaster == newItem.broadcaster

    override fun areContentsTheSame(oldItem: Clip, newItem: Clip) =
        oldItem == newItem
}