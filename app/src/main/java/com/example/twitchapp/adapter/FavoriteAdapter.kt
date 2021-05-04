package com.example.twitchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.twitchapp.R
import com.example.twitchapp.model.data.clipdata.Clip


class FavoriteAdapter(private val context: Context, private var favoriteList: List<Clip>?) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    private lateinit var thumbnailListener: ShowFavoClip
    private lateinit var deleteBtnListener: DeleteItem

    private val viewBinderHelper = ViewBinderHelper()

    inner class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
        var thumbnail: ImageView
        var username: TextView
        var viewer: TextView
        var userProfile: ImageView
        var lang: TextView
        var gameName: TextView
        var swipeRevealLayout: SwipeRevealLayout
        var btnDelete: FrameLayout

        init {
            thumbnail = view.findViewById(R.id.thumbnail)
            username = view.findViewById(R.id.username)
            viewer = view.findViewById(R.id.viewer)
            userProfile = view.findViewById(R.id.user_profile)
            lang = view.findViewById(R.id.lang)
            gameName = view.findViewById(R.id.gamename)
            swipeRevealLayout = view.findViewById(R.id.swipe_layout)
            btnDelete = view.findViewById(R.id.btn_delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.favorite_item,
            parent,
            false
        )
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        viewBinderHelper.bind(holder.swipeRevealLayout, favoriteList!![position].tracking_id);

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
            thumbnailListener.showFavoClip(favoriteList!![position].url)
        }

        holder.btnDelete.setOnClickListener {
            deleteBtnListener.deleteItem(favoriteList!![position])
        }
    }

    override fun getItemCount(): Int {
        return favoriteList!!.size
    }

    interface ShowFavoClip {
        fun showFavoClip(url: String)
    }

    fun setOnThumbnailClickListener(listener: ShowFavoClip) {
        this.thumbnailListener = listener
    }

    interface DeleteItem {
        fun deleteItem(clip: Clip)
    }

    fun setOnDeleteBtnClickListener(listener: DeleteItem) {
        this.deleteBtnListener = listener
    }
}