package com.example.twitchapp.ui.myprofile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twitchapp.databinding.ItemFavoriteBinding
import com.example.twitchapp.model.data.clip_data.Clip
import com.example.twitchapp.ui.ItemClickListener
import com.example.twitchapp.ui.ScreenType
import com.example.twitchapp.util.DIFF_CALLBACK
import com.example.twitchapp.util.convertClipTime
import com.example.twitchapp.util.getGameImage

class MyProfileAdapter(private val context: Context) :
    ListAdapter<Clip, MyProfileAdapter.FavoriteHolder>(DIFF_CALLBACK) {

    private var listener: ItemClickListener? = null

    inner class FavoriteHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clip: Clip) {
            binding.apply {
                clipItemTitle.text = clip.title
                clipDuration.text = convertClipTime(clip.duration)
                userName.text = clip.broadcaster.name
                viewer.text = clip.views.toString()
                Glide.with(context).load(clip.thumbnails.medium)
                    .into(thumbnail)
                val gameImageDrawable = getGameImage(context, clip.game)
                gameImageDrawable?.let {
                    gameImage.setImageDrawable(it)
                }
                thumbnail.apply {
                    setOnClickListener { listener?.thumbnailClickListener(clip.url) }
                    setOnLongClickListener {
                        listener?.longClickListener(clip, ScreenType.FAVORITE)
                        return@setOnLongClickListener true
                    }
                }
                itemFavorite.setOnLongClickListener {
                    listener?.longClickListener(clip, ScreenType.FAVORITE)
                    return@setOnLongClickListener true
                }
                btnMenu.setOnClickListener {
                    listener?.menuClickListener(clip, ScreenType.FAVORITE)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { holder.bind(currentItem) }
    }

    fun setListener(listener: ItemClickListener) {
        this.listener = listener
    }
}