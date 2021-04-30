package com.example.twitchapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.streamdata.Streams
import com.example.twitchapp.model.repository.TwitchRepository
import com.example.twitchapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: TwitchRepository) : ViewModel() {

    val streams: MutableLiveData<Resource<Streams>> = MutableLiveData()
    val clips: MutableLiveData<Resource<Clip>> = MutableLiveData()

    init {
        fetchPubgMobileStream()
        fetchPubgMobileClip()
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

    fun fetchPubgMobileClip() {
        viewModelScope.launch {
            clips.postValue(Resource.Loading())
            val response = repository.fetchPubgMobileClip()
            clips.postValue(handleStreamResponse(response))
        }
    }

    fun fetchApexClip() {
        viewModelScope.launch {
            clips.postValue(Resource.Loading())
            val response = repository.fetchApexClip()
            clips.postValue(handleStreamResponse(response))
        }
    }

    fun fetchAmongusClip() {
        viewModelScope.launch {
            clips.postValue(Resource.Loading())
            val response = repository.fetchAmongusClip()
            clips.postValue(handleStreamResponse(response))
        }
    }

    fun fetchGenshinClip() {
        viewModelScope.launch {
            clips.postValue(Resource.Loading())
            val response = repository.fetchGenshinClip()
            clips.postValue(handleStreamResponse(response))
        }
    }

    fun fetchMinecraftClip() {
        viewModelScope.launch {
            clips.postValue(Resource.Loading())
            val response = repository.fetchMinecratClip()
            clips.postValue(handleStreamResponse(response))
        }
    }

    fun fetchFortniteClip() {
        viewModelScope.launch {
            clips.postValue(Resource.Loading())
            val response = repository.fetchFortniteClip()
            clips.postValue(handleStreamResponse(response))
        }
    }

    fun fetchCallofdutyClip() {
        viewModelScope.launch {
            clips.postValue(Resource.Loading())
            val response = repository.fetchCallofdutyClip()
            clips.postValue(handleStreamResponse(response))
        }
    }

    fun fetchLolClip() {
        viewModelScope.launch {
            clips.postValue(Resource.Loading())
            val response = repository.fetchLolClip()
            clips.postValue(handleStreamResponse(response))
        }
    }

    private fun <T> handleStreamResponse(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}