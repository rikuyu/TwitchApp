package com.example.twitchapp.model.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.twitchapp.mock.MockService
import com.example.twitchapp.model.data.Games
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.util.DummyClip
import com.example.twitchapp.util.DummyStream
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class TwitchRepositoryTest {

    private lateinit var twitchRepository: FakeRepository
    private lateinit var mockService: MockService

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        twitchRepository = FakeRepository()
        mockService = MockService(twitchRepository.delegate)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        twitchRepository.twitchDatabase.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `clipをDAOでRoomDBに挿入できるかのテスト`() = runBlocking {
        val dummy1 = DummyClip.dummyClip
        val dummy2 = DummyClip.dummyClip.copy(tracking_id = "dummy_id_2")
        val dummy3 = DummyClip.dummyClip.copy(tracking_id = "dummy_id_3")
        twitchRepository.apply {
            insertClip(dummy1)
            insertClip(dummy2)
            insertClip(dummy3)
        }
        val expectedValue = listOf(dummy1, dummy2, dummy3)
        val dbData = twitchRepository.getFavoriteGame().toList()
        val measuredValue = dbData.first()
        assertThat(measuredValue).isEqualTo(expectedValue)
        assertThat(measuredValue.size).isEqualTo(3)
    }

    @Test
    fun `clipをDAOでRoomDBから削除できるかのテスト`() = runBlocking {
        val dummy1 = DummyClip.dummyClip
        val dummy2 = DummyClip.dummyClip.copy(tracking_id = "dummy_id_2")
        val dummy3 = DummyClip.dummyClip.copy(tracking_id = "dummy_id_3")
        twitchRepository.apply {
            insertClip(dummy1)
            insertClip(dummy2)
            insertClip(dummy3)
        }
        val premise = twitchRepository.getFavoriteGame().toList().first()
        assertThat(premise).isEqualTo(listOf(dummy1, dummy2, dummy3))
        assertThat(premise.size).isEqualTo(3)

        twitchRepository.apply {
            deleteClip(dummy1)
            deleteClip(dummy2)
            deleteClip(dummy3)
        }
        val expectedValue = listOf<Clip>()
        val clipList = twitchRepository.getFavoriteGame().toList()
        val measuredValue = clipList.first()
        assertThat(measuredValue).isEqualTo(expectedValue)
        assertThat(measuredValue.size).isEqualTo(0)
    }

    @Test
    fun `fetchStreamのテスト`() = runBlocking {
        val expectedValue = DummyStream.dummyStreams
        twitchRepository.behavior.apply {
            setDelay(0, TimeUnit.MILLISECONDS)
            setVariancePercent(0)
            setFailurePercent(0)
            setErrorPercent(0)
        }
        mockService.streamsResponse = DummyStream.dummyStreams
        twitchRepository.streamApi = mockService
        val responseStreams = twitchRepository.fetchStream(1)
        val measuredValue = responseStreams.body()

        assertThat(responseStreams.code()).isEqualTo(STATUS_CODE_OK.first)
        assertThat(responseStreams.message()).isEqualTo(STATUS_CODE_OK.second)
        assertThat(measuredValue).isEqualTo(expectedValue)
    }

    @Test
    fun `fetchClipのテスト`() = runBlocking {
        val expectedValue = DummyClip.dummyClipResponse
        twitchRepository.behavior.apply {
            setDelay(0, TimeUnit.MILLISECONDS)
            setVariancePercent(0)
            setFailurePercent(0)
            setErrorPercent(0)
        }
        mockService.clipResponse = DummyClip.dummyClipResponse
        twitchRepository.streamApi = mockService
        val responseClipResponse = twitchRepository.fetchClip(Games.PUBG_MOBILE.title)
        val measuredValue = responseClipResponse.body()

        assertThat(responseClipResponse.code()).isEqualTo(STATUS_CODE_OK.first)
        assertThat(responseClipResponse.message()).isEqualTo(STATUS_CODE_OK.second)
        assertThat(measuredValue).isEqualTo(expectedValue)
    }

    companion object {
        private val STATUS_CODE_OK = Pair(200, "OK")
    }
}