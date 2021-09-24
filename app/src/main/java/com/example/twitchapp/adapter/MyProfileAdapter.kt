package com.example.twitchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitchapp.databinding.FavoriteItemBinding
import com.example.twitchapp.model.data.clipdata.Clip

class MyProfileAdapter(private val context: Context) :
    ListAdapter<Clip, MyProfileAdapter.FavoriteHolder>(DIFF_CALLBACK) {

    private lateinit var thumbnailListener: ShowFavoClip
    private lateinit var deleteBtnListener: DeleteItem

    inner class FavoriteHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clip: Clip) {
            binding.apply {
                gamename.text = clip.game
                username.text = clip.curator.name
                lang.text = clip.language
                viewer.text = clip.views.toString()

                Glide.with(context).load(clip.thumbnails.medium)
                    .into(thumbnail)

                Glide.with(context).load(clip.thumbnails.medium)
                    .apply(RequestOptions.circleCropTransform())
                    .into(userProfile)

                thumbnail.setOnClickListener {
                    thumbnailListener.showFavoClip(clip.url)
                }

                delete.setOnClickListener {
                    deleteBtnListener.deleteItem(clip)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val binding =
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    interface ShowFavoClip {
        fun showFavoClip(url: String)
    }

    fun setOnThumbnailClickListener(listener: ShowFavoClip) {
        this.thumbnailListener = listener
    }

    interface DeleteItem {
        fun deleteItem(clip: Clip)
    }

    fun setOnDeleteBtnClickListener(listener: DeleteItem) {
        this.deleteBtnListener = listener
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Clip>() {
            override fun areItemsTheSame(oldItem: Clip, newItem: Clip) =
                oldItem.broadcaster == newItem.broadcaster

            override fun areContentsTheSame(oldItem: Clip, newItem: Clip) =
                oldItem == newItem
        }
    }
}