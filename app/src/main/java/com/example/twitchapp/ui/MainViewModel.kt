package com.example.twitchapp.ui

import android.app.Application
import android.content.Context
import android.content.Context.*
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchapp.TwitchApplication
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.data.streamdata.Streams
import com.example.twitchapp.model.repository.TwitchRepository
import com.example.twitchapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel(
    app: Application,
    private val repository: TwitchRepository,
) : AndroidViewModel(app) {

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
            safeFetchStreamCall(gameTitle)
        }
    }

    fun fetchClip(gameTitle: String) {
        viewModelScope.launch {
            clips.postValue(Resource.Loading())
            safeFetchClipCall(gameTitle)
        }
    }

    private suspend fun safeFetchStreamCall(gameTitle: String) {
        streams.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = repository.fetchStream(gameTitle)
                streams.postValue(handleResponseState(response))
            } else {
                streams.postValue(Resource.Error("インターネットに接続してください"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> streams.postValue(Resource.Error("ネットワークエラー"))
                else -> {
                    Log.d("safeFetchStreamCall", t.message!!)
                    streams.postValue(Resource.Error("内部エラー"))
                }
            }
        }
    }

    private suspend fun safeFetchClipCall(gameTitle: String) {
        clips.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = repository.fetchClip(gameTitle)
                clips.postValue(handleResponseState(response))
            } else {
                clips.postValue(Resource.Error("インターネットに接続してください"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> clips.postValue(Resource.Error("ネットワークエラー"))
                else -> {
                    Log.d("safeFetchStreamCall", t.message!!)
                    clips.postValue(Resource.Error("内部エラー"))
                }
            }
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

    fun insertClip(clip: Clip) {
        viewModelScope.launch {
            repository.insertClip(clip)
        }
    }

    fun deleteClip(clip: Clip) {
        viewModelScope.launch {
            repository.deleteClip(clip)
        }
    }

    fun getFavoriteClips() {
        viewModelScope.launch {
            favoriteClips.value = repository.getFavoriteClips()
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<TwitchApplication>().applicationContext.getSystemService(
                CONNECTIVITY_SERVICE
            ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> {
                    Toast.makeText(getApplication(), "Wifi", Toast.LENGTH_LONG).show()
                    return true
                }
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> {
                    Toast.makeText(getApplication(), "CELLULAR", Toast.LENGTH_LONG).show()
                    return true
                }
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> {
                    Toast.makeText(getApplication(), "ETHERNET", Toast.LENGTH_LONG).show()
                    return true
                }
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}