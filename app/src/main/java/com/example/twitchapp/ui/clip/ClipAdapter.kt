package com.example.twitchapp.ui.clip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitchapp.R
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.ItemClickListener
import com.example.twitchapp.ui.ScreenType
import com.example.twitchapp.util.UtilObject
import de.hdodenhof.circleimageview.CircleImageView

class ClipAdapter(
    private val context: Context,
    private val clipList: List<Clip>,
    private val listener: ClipItemClickListener
) :
    RecyclerView.Adapter<ClipAdapter.ClipHolder>() {

    inner class ClipHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item: ConstraintLayout = view.findViewById(R.id.item_clip)
        var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        var clipTitle: TextView = view.findViewById(R.id.clip_title)
        var username: TextView = view.findViewById(R.id.user_name)
        var viewer: TextView = view.findViewById(R.id.viewer)
        var userProfile: ImageView = view.findViewById(R.id.user_profile_image)
        var lang: TextView = view.findViewById(R.id.lang)
        val duration: TextView = view.findViewById(R.id.clip_duration)
        var gameName: TextView = view.findViewById(R.id.game_name)
        var favoIcon: ImageView = view.findViewById(R.id.heart_icon)
        var gameIcon: CircleImageView = view.findViewById(R.id.game_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClipHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_clip, parent, false)
        return ClipHolder(view)
    }

    override fun onBindViewHolder(holder: ClipHolder, position: Int) {
        val clip = clipList[position]

        holder.apply {
            gameName.text = clip.game
            username.text = clip.curator.name
            clipTitle.text = clip.title
            lang.text = clip.language
            viewer.text = clip.views.toString()
            duration.text = UtilObject.convertClipTime(clip.duration)
            thumbnail.setOnClickListener {
                listener.thumbnailClickListener(clip.url)
            }
            userProfile.setOnClickListener {
                listener.userProfileClickListener(clip.broadcaster.channel_url)
            }
            favoIcon.setOnClickListener {
                listener.favoriteIconClickListener(clip)
            }
            item.setOnLongClickListener {
                listener.longClickListener(clip, ScreenType.CLIP)
                return@setOnLongClickListener true
            }
        }
        Glide.with(context).load(clip.thumbnails.medium)
            .into(holder.thumbnail)

        Glide.with(context).load(clip.thumbnails.medium)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.userProfile)

        val gameImageDrawable = UtilObject.getGameImage(context, clip.game)

        gameImageDrawable?.let {
            holder.gameIcon.setImageDrawable(it)
        }
    }

    override fun getItemCount(): Int {
        return clipList.size
    }

    interface ClipItemClickListener : ItemClickListener {
        /*
         * ユーザーのプロフィール画像をクリックしたとき
        */
        fun userProfileClickListener(url: String)

        /*
         * ハートアイコンをクリックしたとき
        */
        fun favoriteIconClickListener(clip: Clip)
    }
}