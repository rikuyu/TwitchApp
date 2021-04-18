package com.example.twitchapp.fragment

import android.content.Intent
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchapp.MainViewModel
import com.example.twitchapp.MainViewModelFactory
import com.example.twitchapp.R
import com.example.twitchapp.adapter.TwitchAdapter
import com.example.twitchapp.model.data.Stream
import com.example.twitchapp.model.repository.TwitchRepository

class StreamFragment : Fragment() {

    private lateinit var repository: TwitchRepository
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var twitchAdapter: TwitchAdapter
    private lateinit var recyclerView: RecyclerView

    private var streamList: List<Stream>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_stream, container, false)

        repository = TwitchRepository()
        viewModelFactory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        mainViewModel.fetchPubgMobileStream()
        recyclerView = view.findViewById(R.id.stream_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val gameIcon: ImageView = view.findViewById(R.id.game_icon)
        gameIcon.outlineProvider = clipOutlineProvider
        gameIcon.clipToOutline = true

        mainViewModel.streams.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()!!.streams.let { streamList = it }
                twitchAdapter = TwitchAdapter(context!!, streamList)
                recyclerView.adapter = twitchAdapter

                twitchAdapter.setOnItemClickListener(object : TwitchAdapter.OnItemClickListener {
                    override fun onItemClickListener(view: View, position: Int) {
                        val uri = Uri.parse(streamList!![position].channel.url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                })
            }
        })

        fetchGameStream(view)

        return view
    }

    private val clipOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setOval(
                0,
                0,
                view.width,
                view.height
            )
        }
    }

    private fun fetchGameStream(view: View) {
        val pubgmobile: ImageView = view.findViewById(R.id.pubg_mobile)
        val apex: ImageView = view.findViewById(R.id.apex)
        val amongus: ImageView = view.findViewById(R.id.amongus)
        val genshin: ImageView = view.findViewById(R.id.genshin)
        val minecraft: ImageView = view.findViewById(R.id.minecraft)
        val fortnite: ImageView = view.findViewById(R.id.fortnite)
        val callofduty: ImageView = view.findViewById(R.id.callofduty)
        val lol: ImageView = view.findViewById(R.id.lol)

        pubgmobile.setOnClickListener {
            mainViewModel.fetchPubgMobileStream()
        }

        apex.setOnClickListener {
            mainViewModel.fetchApexStream()
        }

        amongus.setOnClickListener {
            mainViewModel.fetchAmongusStream()
        }

        genshin.setOnClickListener {
            mainViewModel.fetchGenshinStream()
        }

        minecraft.setOnClickListener {
            mainViewModel.fetchMinecraftStream()
        }

        fortnite.setOnClickListener {
            mainViewModel.fetchFortniteStream()
        }

        callofduty.setOnClickListener {
            mainViewModel.fetchCallofdutyStream()
        }

        lol.setOnClickListener {
            mainViewModel.fetchLolStream()
        }
    }
}