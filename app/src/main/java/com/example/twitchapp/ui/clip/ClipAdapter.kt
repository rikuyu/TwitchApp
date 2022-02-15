package com.example.twitchapp.ui.clip

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitchapp.databinding.ItemClipBinding
import com.example.twitchapp.model.data.clip_data.Clip
import com.example.twitchapp.ui.ItemClickListener
import com.example.twitchapp.ui.ScreenType
import com.example.twitchapp.util.DIFF_CALLBACK
import com.example.twitchapp.util.convertClipTime
import com.example.twitchapp.util.getGameImage

class ClipAdapter(
    private val context: Context,
    private val listener: ClipItemClickListener
) :
    ListAdapter<Clip, ClipAdapter.ClipHolder>(DIFF_CALLBACK) {

    inner class ClipHolder(private val binding: ItemClipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clip: Clip) {
            binding.apply {
                gameName.text = clip.game
                userName.text = clip.curator.name
                clipTitle.text = clip.title
                lang.text = clip.language
                viewer.text = clip.views.toString()
                clipDuration.text = convertClipTime(clip.duration)
                thumbnail.apply {
                    setOnClickListener { listener.thumbnailClickListener(clip.url) }
                    setOnLongClickListener {
                        listener.longClickListener(clip, ScreenType.CLIP)
                        return@setOnLongClickListener true
                    }
                }
                userProfileImage.setOnClickListener {
                    listener.userProfileClickListener(clip.broadcaster.channelUrl)
                }
                heartIcon.setOnClickListener {
                    listener.favoriteIconClickListener(clip)
                }
                itemClip.setOnLongClickListener {
                    listener.longClickListener(clip, ScreenType.CLIP)
                    return@setOnLongClickListener true
                }
                btnMenu.setOnClickListener {
                    listener.menuClickListener(clip, ScreenType.CLIP)
                }
                Glide.with(context).load(clip.thumbnails.medium)
                    .into(thumbnail)

                Glide.with(context).load(clip.thumbnails.medium)
                    .apply(RequestOptions.circleCropTransform())
                    .into(userProfileImage)

                getGameImage(context, clip.game)?.let {
                    gameIcon.setImageDrawable(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClipHolder {
        val binding = ItemClipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClipHolder(binding)
    }

    override fun onBindViewHolder(holder: ClipHolder, position: Int) {
        val clip = getItem(position)
        clip?.let { holder.bind(clip) }
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