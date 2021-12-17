package com.example.twitchapp.ui.myprofile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twitchapp.databinding.ItemFavoriteBinding
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.ItemClickListener
import com.example.twitchapp.util.UtilObject

class MyProfileAdapter(private val context: Context) :
    ListAdapter<Clip, MyProfileAdapter.FavoriteHolder>(DIFF_CALLBACK) {

    private var thumbnailListener: FavoriteItemClickListener? = null
    private var deleteViewListener: FavoriteItemClickListener? = null

    inner class FavoriteHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clip: Clip) {
            binding.apply {
                username.text = clip.curator.name
                viewer.text = clip.views.toString()

                Glide.with(context).load(clip.thumbnails.medium)
                    .into(thumbnail)

                val gameImageDrawable = UtilObject.getGameImage(context, clip.game)
                gameImageDrawable?.let {
                    gameImage.setImageDrawable(it)
                }

                thumbnail.setOnClickListener {
                    thumbnailListener?.thumbnailClickListener(clip.url)
                }

                deleteView.setOnClickListener {
                    deleteViewListener?.deleteViewClickListener(clip)
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
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    interface FavoriteItemClickListener : ItemClickListener {
        /*
         * クリップをスライドしたときに現れるViewをクリックしたとき
        */
        fun deleteViewClickListener(clip: Clip)
    }

    fun setListener(listener: FavoriteItemClickListener) {
        this.thumbnailListener = listener
        this.deleteViewListener = listener
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