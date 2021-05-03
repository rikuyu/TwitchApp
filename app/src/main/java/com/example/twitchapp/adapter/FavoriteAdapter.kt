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
import com.example.twitchapp.model.data.clipdata.Clip

class FavoriteAdapter(private val context: Context, private var favoriteList: List<Clip>?)
    :RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>(){

    private lateinit var listener: ShowFavoClip

    inner class FavoriteHolder(view: View): RecyclerView.ViewHolder(view){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.gameName.text = favoriteList!![position].game
        holder.username.text = favoriteList!![position].curator.name
        holder.lang.text = favoriteList!![position].language
        holder.viewer.text = favoriteList!![position].views.toString()

        Glide.with(context).load(favoriteList!![position].thumbnails.medium)
            .into(holder.thumbnail)

        Glide.with(context).load(favoriteList!![position].thumbnails.medium)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.userProfile)

        holder.thumbnail.setOnClickListener {
            listener.showFavoClip(favoriteList!![position].url)
        }
    }

    override fun getItemCount(): Int {
        return favoriteList!!.size
    }

    interface ShowFavoClip{
        fun showFavoClip(url: String)
    }

    fun setOnThumbnailClickListener(listener: ShowFavoClip){
        this.listener = listener
    }
}