package com.example.twitchapp.ui

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.twitchapp.di.NetworkModule.PAGE_SIZE
import com.example.twitchapp.model.data.Games
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.repository.TwitchRepository
import com.example.twitchapp.util.Resource
import com.example.twitchapp.ui.stream.StreamPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TwitchRepository
) : ViewModel() {

    private val _clips: MutableLiveData<Resource<ClipResponse>> = MutableLiveData()
    val clips: LiveData<Resource<ClipResponse>> get() = _clips
    val favoriteClips: LiveData<List<Clip>> = repository.getFavoriteClips().asLiveData()

    val filterGame: MutableLiveData<Games> = MutableLiveData(Games.ALL)

    init {
        fetchClip("PUBG Mobile")
    }

    val streamFlow = Pager(
        PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE)
    ) {
        StreamPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    fun fetchClip(gameTitle: String) {
        viewModelScope.launch {
            _clips.value = Resource.Loading()
            val cliplist = repository.fetchClip(gameTitle)
            cliplist.let { list ->
                if (list.isSuccessful) {
                    list.body()?.let {
                        _clips.value = Resource.Success(it)
                    }
                } else {
                    _clips.value = Resource.Error("取得失敗")
                }
            }
        }
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
}