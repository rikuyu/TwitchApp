package com.example.twitchapp

import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.app.AppCompatActivity
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
            }
        })

        fetchGameStream()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initTopMenu() {
        pubg_mobile.outlineProvider = clipOutlineProvider
        pubg_mobile.clipToOutline = true
        apex.outlineProvider = clipOutlineProvider
        apex.clipToOutline = true
        amongus.outlineProvider = clipOutlineProvider
        amongus.clipToOutline = true
        genshin.outlineProvider = clipOutlineProvider
        genshin.clipToOutline = true
        minecraft.outlineProvider = clipOutlineProvider
        minecraft.clipToOutline = true
        fortnight.outlineProvider = clipOutlineProvider
        fortnight.clipToOutline = true
        callofduty.outlineProvider = clipOutlineProvider
        callofduty.clipToOutline = true
        lol.outlineProvider = clipOutlineProvider
        lol.clipToOutline = true
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