package com.example.twitchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitchapp.R
import com.example.twitchapp.model.data.streamdata.Stream


class StreamAdapter(private val context: Context, private var streamList: List<Stream>?) :
    RecyclerView.Adapter<StreamAdapter.StreamHolder>() {

    private lateinit var listener: OnItemClickListener

    inner class StreamHolder(view: View) : RecyclerView.ViewHolder(view) {
        var thumbnail: ImageView
        var username: TextView
        var viewer: TextView
        var userProfile: ImageView
        var lang: TextView
        var gameName: TextView

        init {
            thumbnail = view.findViewById(R.id.thumbnail)
            username = view.findViewById(R.id.username)
            viewer = view.findViewById(R.id.viewer)
            userProfile = view.findViewById(R.id.user_profile)
            lang = view.findViewById(R.id.lang)
            gameName = view.findViewById(R.id.gamename)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stream_item, parent, false)
        return StreamHolder(view)
    }

    override fun onBindViewHolder(holder: StreamHolder, position: Int) {
        holder.username.text = streamList!![position].channel.name
        holder.viewer.text = "${streamList!![position].viewers}"

        Glide.with(context).load(streamList!![position].preview.large)
            .into(holder.thumbnail)

        Glide.with(context).load(streamList!![position].channel.logo)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.userProfile)

        holder.lang.text = streamList!![position].channel.language
        holder.gameName.text = streamList!![position].game

        holder.thumbnail.setOnClickListener {
            listener.onItemClickListener(streamList!![position].channel.url)
        }
    }

    override fun getItemCount(): Int {
        return streamList!!.size
    }

    interface OnItemClickListener {
        fun onItemClickListener(url: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}