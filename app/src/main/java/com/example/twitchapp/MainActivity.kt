package com.example.twitchapp

import android.content.Intent
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchapp.adapter.TwitchAdapter
import com.example.twitchapp.model.Stream
import com.example.twitchapp.model.repository.TwitchRepository
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Collections.min
import kotlin.math.min


class MainActivity : AppCompatActivity() {

    private lateinit var repository: TwitchRepository
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var twitchAdapter: TwitchAdapter
    private lateinit var recyclerView: RecyclerView

    private var streamList: List<Stream>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        lifecycleScope.launch {
//            val list = RetrofitInstance.twichApi.getStream()
//            Log.d("通信結果", list.body()!!.streams[0].channel.language)
//        }

        game_icon.outlineProvider = clipOutlineProvider
        game_icon.clipToOutline = true

        repository = TwitchRepository()
        viewModelFactory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        mainViewModel.fetchPubgMobileStream()
        initRecyclerView()

        mainViewModel.streams.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()!!.streams.let { streamList = it }
                twitchAdapter = TwitchAdapter(this, streamList)
                recyclerView.adapter = twitchAdapter

                twitchAdapter.setOnItemClickListener(object:TwitchAdapter.OnItemClickListener{
                    override fun onItemClickListener(view: View, position: Int) {
                        val uri = Uri.parse(streamList!![position].channel.url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                })
            }
        })

        fetchGameStream()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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

    private fun fetchGameStream() {
        pubg_mobile.setOnClickListener {
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

        fortnight.setOnClickListener {
            mainViewModel.fetchFortnightStream()
        }

        callofduty.setOnClickListener {
            mainViewModel.fetchCallofdutyStream()
        }

        lol.setOnClickListener {
            mainViewModel.fetchLolStream()
        }
    }
}