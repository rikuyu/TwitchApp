package com.example.twitchapp.adapter

import android.content.Context
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitchapp.R
import com.example.twitchapp.model.Stream


class TwitchAdapter(private val context: Context) :
    RecyclerView.Adapter<TwitchAdapter.TwitchHolder>() {

    private var streamList = ArrayList<Stream>()

    inner class TwitchHolder(view: View) : RecyclerView.ViewHolder(view) {
        var thumbnail: ImageView
        var username: TextView
        var viewer: TextView
        var userProfile: ImageView
        var streamLink: TextView

        init {
            thumbnail = view.findViewById(R.id.thumbnail)
            username = view.findViewById(R.id.username)
            viewer = view.findViewById(R.id.viewer)
            userProfile = view.findViewById(R.id.user_profile)
            streamLink = view.findViewById(R.id.stream_link)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitchHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stream_item, parent, false)
        return TwitchHolder(view)
    }

    override fun onBindViewHolder(holder: TwitchHolder, position: Int) {
        holder.username.text = streamList[position].channel.name
        holder.viewer.text = "${streamList[position].viewers} 人視聴中"

        Glide.with(context).load(streamList[position].preview.large)
            .apply(RequestOptions().override(100, 70))
            .into(holder.thumbnail)

        Glide.with(context).load(streamList[position].channel.logo)
            .apply(RequestOptions().override(50, 50))
            .into(holder.userProfile)

        //holder.streamLink.linksClickable = true

        holder.streamLink.movementMethod = LinkMovementMethod.getInstance()
        val link: CharSequence =
            Html.fromHtml("<a href=\"${streamList[position].channel.url}\">視聴する</a>")
        holder.streamLink.text = link
    }

    override fun getItemCount(): Int {
        return streamList.size
    }
}