package com.example.twitchapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.twitchapp.R
import com.example.twitchapp.model.Stream


class TwitchAdapter(private val context: Context, private var streamList: List<Stream>?) :
    RecyclerView.Adapter<TwitchAdapter.TwitchHolder>() {

    lateinit var listener: OnItemClickListener

    inner class TwitchHolder(view: View) : RecyclerView.ViewHolder(view) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitchHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stream_item, parent, false)
        return TwitchHolder(view)
    }

    override fun onBindViewHolder(holder: TwitchHolder, position: Int) {
        holder.username.text = streamList!![position].channel.name
        holder.viewer.text = "${streamList!![position].viewers}"

        Glide.with(context).load(streamList!![position].preview.large)
            .into(holder.thumbnail)

        Glide.with(context).load(streamList!![position].channel.logo)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.userProfile)

        //holder.streamLink.linksClickable = true

//        holder.streamLink.movementMethod = LinkMovementMethod.getInstance()
//        val link: CharSequence =
//            Html.fromHtml("<a href=\"${streamList!![position].channel.url}\">視聴する</a>")
//        holder.streamLink.text = link

        holder.lang.text = streamList!![position].channel.language.toUpperCase()
        holder.gameName.text = streamList!![position].game

        holder.thumbnail.setOnClickListener {
            listener.onItemClickListener(it, position)
        }
    }

    override fun getItemCount(): Int {
        return streamList!!.size
    }

    fun setList(list: List<Stream>?) {
        streamList = list
    }

    interface OnItemClickListener {
        fun onItemClickListener(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}