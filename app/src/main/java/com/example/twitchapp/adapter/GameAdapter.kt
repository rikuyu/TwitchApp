package com.example.twitchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twitchapp.R
import com.example.twitchapp.model.data.Game

class GameAdapter(private val context: Context, private val gameList: MutableList<String>) :
    RecyclerView.Adapter<GameAdapter.GameHolder>() {

    inner class GameHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameImg: ImageView

        init {
            gameImg = view.findViewById(R.id.game_img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return GameHolder(view)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        Glide.with(context).load(gameList[position]).into(holder.gameImg)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }
}