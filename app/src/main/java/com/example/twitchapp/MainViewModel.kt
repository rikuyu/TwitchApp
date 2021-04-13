package com.example.twitchapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchapp.model.Streams
import com.example.twitchapp.model.repository.TwitchRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: TwitchRepository): ViewModel() {

    val streams: MutableLiveData<Response<Streams>> = MutableLiveData()

    fun getStream(){
        viewModelScope.launch {
            streams.value = repository.getStream()
        }
    }
}