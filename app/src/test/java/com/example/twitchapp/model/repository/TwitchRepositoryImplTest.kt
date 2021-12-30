package com.example.twitchapp.model.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.util.DummyClip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import com.google.common.truth.Truth.assertThat

@ExperimentalCoroutinesApi
@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class TwitchRepositoryImplTest {

    private lateinit var twitchRepository: FakeRepository

    @Before
    fun createRepository() {
        twitchRepository = FakeRepository()
    }

    @After
    fun cleanup() {
        twitchRepository.twitchDatabase.close()
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
}