package com.example.twitchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitchapp.R
import com.example.twitchapp.model.data.streamdata.Stream

class StreamAdapter(private val context: Context) :
    PagingDataAdapter<Stream, StreamAdapter.StreamHolder>(DIFF_CALLBACK) {

    private lateinit var listener: OnItemClickListener

    inner class StreamHolder(view: View) : RecyclerView.ViewHolder(view) {
        var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        var username: TextView = view.findViewById(R.id.username)
        var viewer: TextView = view.findViewById(R.id.viewer)
        var userProfile: ImageView = view.findViewById(R.id.user_profile)
        var lang: TextView = view.findViewById(R.id.lang)
        var gameName: TextView = view.findViewById(R.id.gamename)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stream_item, parent, false)
        return StreamHolder(view)
    }

    override fun onBindViewHolder(holder: StreamHolder, position: Int) {
        val stream = getItem(position)

        holder.username.text = stream!!.channel.name
        holder.viewer.text = "${stream.viewers}"
        Glide.with(context).load(stream.preview.large)
            .into(holder.thumbnail)
        Glide.with(context).load(stream.channel.logo)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.userProfile)
        holder.lang.text = stream.channel.language
        holder.gameName.text = stream.game
        holder.thumbnail.setOnClickListener {
            listener.onThumbnailClickListener(stream.channel.url)
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