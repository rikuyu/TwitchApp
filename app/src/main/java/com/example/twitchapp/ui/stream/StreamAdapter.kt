package com.example.twitchapp.ui.stream

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitchapp.databinding.ItemStreamBinding
import com.example.twitchapp.model.data.streamdata.Stream
import com.example.twitchapp.ui.ItemClickListener
import com.example.twitchapp.ui.ScreenType

class StreamAdapter(private val context: Context) :
    PagingDataAdapter<Stream, StreamAdapter.StreamHolder>(DIFF_CALLBACK) {

    private var listener: ItemClickListener? = null

    inner class StreamHolder(private val binding: ItemStreamBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stream: Stream) {
            binding.apply {
                userName.text = stream.channel.name
                viewer.text = stream.viewers.toString()
                Glide.with(context)
                    .load(stream.preview.medium)
                    .into(thumbnail)
                Glide.with(context).load(stream.channel.logo)
                    .apply(RequestOptions.circleCropTransform())
                    .into(userProfileImage)
                lang.text = stream.channel.language
                gameName.text = stream.game
                thumbnail.apply {
                    setOnClickListener { listener?.thumbnailClickListener(stream.channel.url) }
                    setOnLongClickListener {
                        listener?.longClickListener(stream, ScreenType.STREAM)
                        return@setOnLongClickListener true
                    }
                }
                itemStream.setOnLongClickListener {
                    listener?.longClickListener(stream, ScreenType.STREAM)
                    return@setOnLongClickListener true
                }
                btnMenu.setOnClickListener {
                    listener?.menuClickListener(stream, ScreenType.STREAM)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamHolder {
        val binding = ItemStreamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StreamHolder(binding)
    }

    override fun onBindViewHolder(holder: StreamHolder, position: Int) {
        val stream = getItem(position)
        stream?.let { holder.bind(it) }
    }

    fun setListener(listener: ItemClickListener) {
        this.listener = listener
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Stream>() {
            override fun areItemsTheSame(oldItem: Stream, newItem: Stream) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Stream, newItem: Stream) =
                oldItem == newItem
        }
    }
}