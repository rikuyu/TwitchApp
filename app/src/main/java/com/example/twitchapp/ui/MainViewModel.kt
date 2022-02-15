package com.example.twitchapp.ui

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.twitchapp.model.data.Games
import com.example.twitchapp.model.data.clip_data.Clip
import com.example.twitchapp.model.repository.TwitchRepository
import com.example.twitchapp.util.Status
import com.example.twitchapp.ui.stream.StreamPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TwitchRepository
) : ViewModel() {

    private val _clips: MutableLiveData<Status<List<Clip>>> = MutableLiveData()
    val clips: LiveData<Status<List<Clip>>> get() = _clips
    // val clips: LiveData<Status<ClipResponse>> = _clips.distinctUntilChanged()

    val filterGame: MutableLiveData<Games> = MutableLiveData(Games.ALL)

    private val _favoriteList: MutableStateFlow<Status<List<Clip>>> = MutableStateFlow(Status.Loading)
    val favoriteList: StateFlow<Status<List<Clip>>> get() = _favoriteList

    val streamFlow = Pager(
        PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = PAGE_SIZE)
    ) {
        StreamPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    fun fetchClip(gameTitle: String) = viewModelScope.launch {
        _clips.value = Status.Loading
        runCatching {
            withContext(Dispatchers.IO) {
                val res = repository.fetchClip(gameTitle)
                if (!res.isSuccessful || res.body() == null) throw Throwable()
                return@withContext res.body()!!.clips
            }
        }.onSuccess {
            _clips.value = Status.Success(it)
        }.onFailure {
            _clips.value = Status.Error(it)
        }
    }

    fun getFavoriteGame() = viewModelScope.launch {
        _favoriteList.value = Status.Loading
        repository.getFavoriteGame().catch {
            _favoriteList.value = Status.Error(it)
        }.collect {
            _favoriteList.value = Status.Success(it)
        }
    }

    fun insertGetClip(clip: Clip) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertClip(clip)
                if (filterGame.value == Games.ALL || filterGame.value == null) {
                    getFavoriteGame()
                    return@withContext
                }
                filterGame.value?.let {
                    getSpecificFavoriteGame(it.title)
                }
            }
        }
    }

    fun deleteClip(clip: Clip) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteClip(clip)
                if (filterGame.value == Games.ALL || filterGame.value == null) {
                    getFavoriteGame()
                    return@withContext
                }
                filterGame.value?.let {
                    getSpecificFavoriteGame(it.title)
                }
            }
        }
    }

    fun getSpecificFavoriteGame(game: String) = viewModelScope.launch {
        repository.getFavoriteGame().catch {
            _favoriteList.value = Status.Error(it)
        }.collect { list ->
            if (game != Games.ALL.title) {
                val filteredList = list.filter { it.game == game }
                _favoriteList.value = Status.Success(filteredList)
            } else {
                _favoriteList.value = Status.Success(list)
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 7
    }
}