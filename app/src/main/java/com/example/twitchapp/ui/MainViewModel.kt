package com.example.twitchapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchapp.model.data.Game
import com.example.twitchapp.model.data.Streams
import com.example.twitchapp.model.repository.TwitchRepository
import com.example.twitchapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: TwitchRepository) : ViewModel() {

    val streams: MutableLiveData<Resource<Streams>> = MutableLiveData()
    lateinit var games: MutableList<String>

    init {
        fetchPubgMobileStream()
    }

    fun fetchPubgMobileStream() {
        viewModelScope.launch {
            streams.postValue(Resource.Loading())
            val response = repository.fetchPubgMobileStream()
            streams.postValue(handleStreamResponse(response))
        }
    }

    fun fetchApexStream() {
        viewModelScope.launch {
            streams.postValue(Resource.Loading())
            val response = repository.fetchApexStream()
            streams.postValue(handleStreamResponse(response))
        }
    }

    fun fetchAmongusStream() {
        viewModelScope.launch {
            streams.postValue(Resource.Loading())
            val response = repository.fetchAmongusStream()
            streams.postValue(handleStreamResponse(response))
        }
    }

    fun fetchGenshinStream() {
        viewModelScope.launch {
            streams.postValue(Resource.Loading())
            val response = repository.fetchGenshinStream()
            streams.postValue(handleStreamResponse(response))
        }
    }

    fun fetchMinecraftStream() {
        viewModelScope.launch {
            streams.postValue(Resource.Loading())
            val response = repository.fetchMinecratStream()
            streams.postValue(handleStreamResponse(response))
        }
    }

    fun fetchFortniteStream() {
        viewModelScope.launch {
            streams.postValue(Resource.Loading())
            val response = repository.fetchFortniteStream()
            streams.postValue(handleStreamResponse(response))
        }
    }

    fun fetchCallofdutyStream() {
        viewModelScope.launch {
            streams.postValue(Resource.Loading())
            val response = repository.fetchCallofdutyStream()
            streams.postValue(handleStreamResponse(response))
        }
    }

    fun fetchLolStream() {
        viewModelScope.launch {
            streams.postValue(Resource.Loading())
            val response = repository.fetchLolStream()
            streams.postValue(handleStreamResponse(response))
        }
    }

    fun createGameList(): MutableList<String> {
        games = repository.createGameList()
        return games
    }

    private fun handleStreamResponse(response: Response<Streams>): Resource<Streams> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}