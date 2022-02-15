package com.example.twitchapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.twitchapp.model.data.clip_data.ClipResponse
import com.example.twitchapp.model.repository.TwitchRepository
import com.example.twitchapp.util.CoroutineTestRule
import com.example.twitchapp.util.Dummy
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var repository: TwitchRepository

    @MockK
    private lateinit var dummyResponse: Response<ClipResponse>

    private val dummyClipResponse: ClipResponse = ClipResponse(listOf())

    private lateinit var viewModel: MainViewModel

    private val dummyStreams = Dummy.dummyStreams

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchClip() の成功パターン`() = runBlocking {
//        val observer = mockk<Observer<Status<List<Clip>>>> {
//            every { onChanged(any()) } just Runs
//        }
//
//        viewModel.clips.observeForever(observer)
//
//        coEvery { repository.fetchClip(any()) } returns dummyResponse
//        coEvery { dummyResponse.isSuccessful } returns true
//        coEvery { dummyResponse.body() } returns dummyClipResponse
//
//        viewModel.fetchClip("").join()
//
//        coVerify() {
//            repository.fetchClip(any())
//        }
//
//        verifySequence {
//            observer.onChanged(Status.Loading)
//            observer.onChanged(Status.Success(dummyClipResponse.clips))
//        }
    }

    @Test
    fun `fetchClip() の失敗パターン`() = runBlocking {
//        val observer = mockk<Observer<Status<ClipResponse>>>(relaxed = true)
//        viewModel.clips.observeForever(observer)
//
//        coEvery { repository.fetchClip(any()) } returns dummyResponse
//        coEvery { dummyResponse.isSuccessful } returns false
//
//        viewModel.fetchClip("")
//
//        verifySequence {
//            observer.onChanged(Status.Loading)
//            observer.onChanged(Status.Error("取得失敗"))
//        }
    }
}