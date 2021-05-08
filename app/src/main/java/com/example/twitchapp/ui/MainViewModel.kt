package com.example.twitchapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.data.streamdata.Streams
import com.example.twitchapp.model.repository.TwitchRepository
import com.example.twitchapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: TwitchRepository) : ViewModel() {

    val streams: MutableLiveData<Resource<Streams>> = MutableLiveData()
    val clips: MutableLiveData<Resource<ClipResponse>> = MutableLiveData()
    val favoriteClips: MutableLiveData<List<Clip>> = MutableLiveData()

    init {
        fetchStream("PUBG Mobile")
        fetchClip("PUBG Mobile")
        getFavoriteClips()
    }

    fun fetchStream(gameTitle: String) {
        viewModelScope.launch {
            streams.postValue(Resource.Loading())
            val response = repository.fetchStream(gameTitle)
            streams.postValue(handleResponseState(response))
        }
    }

    fun fetchClip(gameTitle: String) {
        viewModelScope.launch {
            clips.postValue(Resource.Loading())
            val response = repository.fetchClip(gameTitle)
            clips.postValue(handleResponseState(response))
        }
    }

    private fun <T> handleResponseState(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun insertClip(clip: Clip){
        viewModelScope.launch {
            repository.insertClip(clip)
        }
    }

    fun deleteClip(clip: Clip){
        viewModelScope.launch {
            repository.deleteClip(clip)
        }
    }

    fun getFavoriteClips(){
        viewModelScope.launch {
            favoriteClips.value = repository.getFavoriteClips()
        }
    }
}