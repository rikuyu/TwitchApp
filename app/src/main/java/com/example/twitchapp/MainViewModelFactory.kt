package com.example.twitchapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.twitchapp.model.repository.TwitchRepository

class MainViewModelFactory(private val repository: TwitchRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}