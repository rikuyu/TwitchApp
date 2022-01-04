package com.example.twitchapp.mock

import com.example.twitchapp.model.api.TwitchApi
import com.example.twitchapp.model.data.clipdata.ClipResponse
import com.example.twitchapp.model.data.streamdata.Streams
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate

class MockService(
    private val delegate: BehaviorDelegate<TwitchApi>
) : TwitchApi {

    var streamsResponse: Streams? = null
    var clipResponse: ClipResponse? = null

    override suspend fun fetchStream(pageSize: Int, page: Int): Response<Streams> {
        return delegate.returningResponse(streamsResponse).fetchStream(pageSize = pageSize, page = page)
    }

    override suspend fun fetchClip(trend: Boolean, period: String, gameTitle: String): Response<ClipResponse> {
        return delegate.returningResponse(clipResponse).fetchClip(gameTitle = gameTitle)
    }
}