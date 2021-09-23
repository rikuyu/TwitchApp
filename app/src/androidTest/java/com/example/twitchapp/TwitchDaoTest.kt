package com.example.twitchapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.twitchapp.db.TwitchDao
import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.model.data.clipdata.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @Test
    fun insertClipToDatabase() = runBlockingTest {
        val dummyBroadcaster = Broadcaster(
            channel_url = "dummy_url",
            display_name = null,
            id = null,
            logo = null,
            name = "dummy_broadcaster_name"
        )

        val dummyVod = Vod(id = "dummy_id", url = null)
        val dummyCurator = Curator(
            channel_url = null,
            display_name = null,
            id = null,
            logo = null,
            name = "dummy_name"
        )
        val dummyThumbnails = Thumbnails(
            medium = "dummy_thumbnail",
            small = null,
            tiny = null
        )

        val dummyClip = Clip(
            tracking_id = "dummy_clip_tracking_id",
            broadcaster = dummyBroadcaster,
            created_at = "dummy_string",
            curator = dummyCurator,
            duration = 10.0,
            embed_html = "dummy_string",
            embed_url = "dummy_string",
            game = "dummy_string",
            language = "dummy_string",
            slug = "dummy_string",
            thumbnails = dummyThumbnails,
            title = "dummy_string",
            url = "dummy_string",
            views = 10,
            vod = dummyVod,
        )

        dao.insertClip(dummyClip)
        val allFavoriteClips = dao.getAllClips()

        //assertThat(allFavoriteClips).contains(dummyClip)
    }

    @Test
    fun deleteClipToDatabase() = runBlockingTest {
        val dummyBroadcaster = Broadcaster(
            channel_url = "dummy_url",
            display_name = null,
            id = null,
            logo = null,
            name = "dummy_broadcaster_name",
        )

        val dummyVod = Vod(id = "dummy_id", url = null)
        val dummyCurator = Curator(
            channel_url = null,
            display_name = null,
            id = null,
            logo = null,
            name = "dummy_name"
        )
        val dummyThumbnails = Thumbnails(
            medium = "dummy_thumbnail",
            small = null,
            tiny = null
        )

        val dummyClip = Clip(
            tracking_id = "dummy_clip_tracking_id",
            broadcaster = dummyBroadcaster,
            created_at = "dummy_string",
            curator = dummyCurator,
            duration = 10.0,
            embed_html = "dummy_string",
            embed_url = "dummy_string",
            game = "dummy_string",
            language = "dummy_string",
            slug = "dummy_string",
            thumbnails = dummyThumbnails,
            title = "dummy_string",
            url = "dummy_string",
            views = 10,
            vod = dummyVod,
        )

        dao.insertClip(dummyClip)
        dao.deleteClip(dummyClip)

        val allFavoriteClips = dao.getAllClips()
        //assertThat(allFavoriteClips).doesNotContain(dummyClip)
    }
}