package com.example.twitchapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.twitchapp.db.TwitchDao
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var twitchDao: TwitchDao

    @Before
    fun setupViewModel() {
//        mainViewModel = MainViewModel()
//        twitchDao = TwitchDatabase.twitchDao()
    }
}