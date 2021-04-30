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
import com.example.twitchapp.model.data.clipdata.ClipItem

class ClipAdapter(private val context: Context, private val clipList: List<ClipItem>?) :
    RecyclerView.Adapter<ClipAdapter.ClipHolder>() {

    inner class ClipHolder(view: View) : RecyclerView.ViewHolder(view) {
        var thumbnail: ImageView
        var username: TextView
        var viewer: TextView
        var userProfile: ImageView
        var lang: TextView
        var gameName: TextView
        var favoIcon: ImageView

        init {
            thumbnail = view.findViewById(R.id.thumbnail)
            username = view.findViewById(R.id.username)
            viewer = view.findViewById(R.id.viewer)
            userProfile = view.findViewById(R.id.user_profile)
            lang = view.findViewById(R.id.lang)
            gameName = view.findViewById(R.id.gamename)
            favoIcon = view.findViewById(R.id.heart_icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClipHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.clip_item, parent, false)
        return ClipHolder(view)
    }

    override fun onBindViewHolder(holder: ClipHolder, position: Int) {
        holder.gameName.text = clipList!![position].game
        holder.username.text = clipList!![position].curator.name
        holder.lang.text = clipList!![position].language

        Glide.with(context).load(clipList!![position].thumbnails.medium)
            .into(holder.thumbnail)

        Glide.with(context).load(clipList!![position].thumbnails.medium)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.userProfile)
    }

    override fun getItemCount(): Int {
        return clipList!!.size
    }
}