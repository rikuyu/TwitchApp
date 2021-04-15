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

        initTopGameMenu()

        repository = TwitchRepository()
        viewModelFactory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        mainViewModel.getStream()
        initRecyclerView()

        mainViewModel.streams.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()!!.streams.let { streamList = it }
                twitchAdapter = TwitchAdapter(this, streamList)
                recyclerView.adapter = twitchAdapter
            }
        })
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initTopGameMenu(){
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
}