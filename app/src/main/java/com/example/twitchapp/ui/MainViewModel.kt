package com.example.twitchapp.ui

import android.app.Application
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.example.twitchapp.TwitchApplication
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.data.streamdata.Streams
import com.example.twitchapp.model.repository.TwitchRepository
import com.example.twitchapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    private val repository: TwitchRepository
) : AndroidViewModel(app) {

    private val _streams: MutableLiveData<Resource<Streams>> = MutableLiveData()
    val streams: LiveData<Resource<Streams>> get() = _streams

    private val _clips: MutableLiveData<Resource<ClipResponse>> = MutableLiveData()
    val clips: LiveData<Resource<ClipResponse>> get() = _clips

    val favoriteClips: LiveData<List<Clip>> = repository.getFavoriteClips().asLiveData()

    init {
        fetchStream("PUBG Mobile")
        fetchClip("PUBG Mobile")
        getFavoriteClips()
    }

    // viewModelScopeはメインスレッド上で実行されるため、postValueではなく、setValue
    fun fetchStream(gameTitle: String) {
        viewModelScope.launch {
            _streams.value = Resource.Loading()
            safeFetchStreamCall(gameTitle)
        }
    }

    fun fetchClip(gameTitle: String) {
        viewModelScope.launch {
            _clips.value = Resource.Loading()
            safeFetchClipCall(gameTitle)
        }
    }

    private suspend fun safeFetchStreamCall(gameTitle: String) {
        _streams.value = Resource.Loading()
        try {
            if (hasInternetConnection()) {
                val response = repository.fetchStream(gameTitle)
                _streams.setValue(handleResponseState(response))
            } else {
                _streams.setValue(Resource.Error("インターネットに接続してください"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _streams.setValue(Resource.Error(t.message))
                else -> {
                    Log.d("safeFetchStreamCall", t.message!!)
                    _streams.setValue(Resource.Error("内部エラー"))
                }
            }
        }
    }

    private suspend fun safeFetchClipCall(gameTitle: String) {
        _clips.value = Resource.Loading()
        try {
            if (hasInternetConnection()) {
                val response = repository.fetchClip(gameTitle)
                _clips.setValue(handleResponseState(response))
            } else {
                _clips.setValue(Resource.Error("インターネットに接続してください"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _clips.setValue(Resource.Error(t.message))
                else -> {
                    Log.d("safeFetchStreamCall", t.message!!)
                    _clips.setValue(Resource.Error("内部エラー"))
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

    fun insertGetClip(clip: Clip) {
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
                    Log.d("Network Type", "Wifi")
                    true
                }
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> {
                    // 3Gや4G／LTEの電波を使用できるデータ通信をセルラー（CELLULAR）という
                    Log.d("Network Type", "CELLULAR")
                    true
                }
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> {
                    Log.d("Network Type", "ETHERNET")
                    true
                }
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI, TYPE_MOBILE, TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}