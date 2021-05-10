package com.example.twitchapp.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.twitchapp.TwitchApplication
import com.example.twitchapp.model.repository.TwitchRepository

class MainViewModelFactory(
    private val app: Application,
    private val repository: TwitchRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(app, repository) as T
    }
}