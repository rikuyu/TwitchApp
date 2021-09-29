package com.example.twitchapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.twitchapp.model.repository.TwitchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MainViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: TwitchRepository

//    @Before
//    fun setupViewModel(){
//
//        repository = TwitchRepository(
//
//        )
//
//        viewModel = MainViewModel(
//            ApplicationProvider.getApplicationContext(),
//
//
//        )
//    }
}