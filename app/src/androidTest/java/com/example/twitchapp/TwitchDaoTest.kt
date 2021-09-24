package com.example.twitchapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.twitchapp.db.TwitchDao
import com.example.twitchapp.db.TwitchDatabase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TwitchDaoTest {

    private lateinit var database: TwitchDatabase
    private lateinit var dao: TwitchDao
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TwitchDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.twitchDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    // 落ちる（原因不明）Error: This job has not completed yet
    @Test
    fun insertClipToDatabase() = testScope.runBlockingTest {
        val dummyClip = Dummy.dummyClip
        dao.insertClip(dummyClip)
        val allFavoriteClips = dao.getAllClips().toList()

        assertThat(allFavoriteClips).isEqualTo(dummyClip)
    }

    // 落ちる（原因不明）Error: This job has not completed yet
    @Test
    fun deleteClipToDatabase() = testScope.runBlockingTest {
        val dummyClip = Dummy.dummyClip
        dao.insertClip(dummyClip)

        val insertedClips = dao.getAllClips().toList()
        assertThat(insertedClips).isEqualTo(dummyClip)

        dao.deleteClip(dummyClip)
        val deletedClips = dao.getAllClips().toList()

        assertThat(deletedClips).doesNotContain(dummyClip)
    }
}