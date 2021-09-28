package com.example.twitchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitchapp.databinding.StreamItemBinding
import com.example.twitchapp.model.data.streamdata.Stream

class StreamAdapter(private val context: Context) :
    PagingDataAdapter<Stream, StreamAdapter.StreamHolder>(DIFF_CALLBACK) {

    private lateinit var listener: OnItemClickListener

    inner class StreamHolder(private val binding: StreamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(stream: Stream) {
            binding.apply {
                username.text = stream.channel.name
                viewer.text = stream.viewers.toString()
                Glide.with(context)
                    .load(stream.preview.large)
                    .into(thumbnail)
                Glide.with(context).load(stream.channel.logo)
                    .apply(RequestOptions.circleCropTransform())
                    .into(userProfile)
                lang.text = stream.channel.language
                gamename.text = stream.game
                thumbnail.setOnClickListener {
                    listener.onThumbnailClickListener(stream.channel.url)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamHolder {
        val binding = StreamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StreamHolder(binding)
    }

    override fun onBindViewHolder(holder: StreamHolder, position: Int) {
        val stream = getItem(position)
        if (stream != null) {
            holder.bind(stream)
        }
    }

    interface OnItemClickListener {
        fun onThumbnailClickListener(url: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Stream>() {
            override fun areItemsTheSame(oldItem: Stream, newItem: Stream) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Stream, newItem: Stream) =
                oldItem == newItem
        }
    }
}