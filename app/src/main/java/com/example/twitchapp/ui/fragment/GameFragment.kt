package com.example.twitchapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.ui.MainViewModelFactory
import com.example.twitchapp.R
import com.example.twitchapp.adapter.GameAdapter
import com.example.twitchapp.model.repository.TwitchRepository

class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var repository: TwitchRepository
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var gameAdapter: GameAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = TwitchRepository()
        viewModelFactory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        recyclerView = view.findViewById(R.id.game_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)

        gameAdapter = GameAdapter(requireContext(), mainViewModel.createGameList())
        recyclerView.adapter = gameAdapter
    }
}