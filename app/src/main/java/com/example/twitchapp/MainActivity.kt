package com.example.twitchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchapp.R
import com.example.twitchapp.adapter.TwitchAdapter
import com.example.twitchapp.model.RetrofitInstance
import com.example.twitchapp.model.repository.TwitchRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var repository: TwitchRepository
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var twitchAdapter: TwitchAdapter
    private lateinit var recyclerView: RecyclerView

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

        mainViewModel.getStream()

        mainViewModel.streams.observe(this, Observer { response ->
            {

            }
        })
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}