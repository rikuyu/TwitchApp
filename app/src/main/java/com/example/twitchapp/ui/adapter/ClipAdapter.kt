package com.example.twitchapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitchapp.R
import com.example.twitchapp.model.data.clipdata.Clip

class ClipAdapter(private val context: Context, private val clipList: List<Clip>?) :
    RecyclerView.Adapter<ClipAdapter.ClipHolder>() {

    private lateinit var thumbnailListener: OnItemClickListener
    private lateinit var userProfileListener: OnItemClickListener
    private lateinit var favoIconListener: OnItemClickListener

    inner class ClipHolder(view: View) : RecyclerView.ViewHolder(view) {
        var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        var username: TextView = view.findViewById(R.id.username)
        var viewer: TextView = view.findViewById(R.id.viewer)
        var userProfile: ImageView = view.findViewById(R.id.user_profile)
        var lang: TextView = view.findViewById(R.id.lang)
        var gameName: TextView = view.findViewById(R.id.gamename)
        var favoIcon: ToggleButton = view.findViewById(R.id.heart_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClipHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_clip, parent, false)
        return ClipHolder(view)
    }

    override fun onBindViewHolder(holder: ClipHolder, position: Int) {
        holder.gameName.text = clipList!![position].game
        holder.username.text = clipList[position].curator.name
        holder.lang.text = clipList[position].language
        holder.viewer.text = clipList[position].views.toString()
        holder.favoIcon.text = null
        holder.favoIcon.textOn = null
        holder.favoIcon.textOff = null

        Glide.with(context).load(clipList[position].thumbnails.medium)
            .into(holder.thumbnail)

        Glide.with(context).load(clipList[position].thumbnails.medium)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.userProfile)

        holder.thumbnail.setOnClickListener {
            thumbnailListener.showClip(clipList[position].url)
        }

        holder.userProfile.setOnClickListener {
            userProfileListener.showProfile(clipList[position].broadcaster.channel_url)
        }

        holder.favoIcon.setOnClickListener {
            favoIconListener.addClip(clipList[position])
        }
    }

    override fun getItemCount(): Int {
        return clipList?.size ?: 0
    }

    interface OnItemClickListener {
        fun showClip(url: String)
        fun showProfile(url: String)
        fun addClip(clip: Clip)
    }

    fun setOnThumbnailClickListener(listener: OnItemClickListener) {
        this.thumbnailListener = listener
        this.userProfileListener = listener
        this.favoIconListener = listener
    }
}