package com.example.twitchapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchapp.model.data.Game
import com.example.twitchapp.model.data.Streams
import com.example.twitchapp.model.repository.TwitchRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: TwitchRepository) : ViewModel() {

    val streams: MutableLiveData<Response<Streams>> = MutableLiveData()
    lateinit var games: MutableList<String>

    init {
        fetchPubgMobileStream()
    }

    fun fetchPubgMobileStream() {
        viewModelScope.launch {
            streams.value = repository.fetchPubgMobileStream()
        }
    }

    fun fetchApexStream() {
        viewModelScope.launch {
            streams.value = repository.fetchApexStream()
        }
    }

    fun fetchAmongusStream() {
        viewModelScope.launch {
            streams.value = repository.fetchAmongusStream()
        }
    }

    fun fetchGenshinStream() {
        viewModelScope.launch {
            streams.value = repository.fetchGenshinStream()
        }
    }

    fun fetchMinecraftStream() {
        viewModelScope.launch {
            streams.value = repository.fetchMinecratStream()
        }
    }

    fun fetchFortniteStream() {
        viewModelScope.launch {
            streams.value = repository.fetchFortniteStream()
        }
    }

    fun fetchCallofdutyStream() {
        viewModelScope.launch {
            streams.value = repository.fetchCallofdutyStream()
        }
    }

    fun fetchLolStream() {
        viewModelScope.launch {
            streams.value = repository.fetchLolStream()
        }
    }

    fun createGameList(): MutableList<String>{
        games = repository.createGameList()
        return games
    }
}