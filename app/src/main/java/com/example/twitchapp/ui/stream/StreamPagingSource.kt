package com.example.twitchapp.ui.stream

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.twitchapp.model.data.streamdata.Stream
import com.example.twitchapp.model.repository.TwitchRepository

class StreamPagingSource(private val repository: TwitchRepository) : PagingSource<Int, Stream>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Stream> {
        return try {
            val page = params.key ?: 1
            val response = repository.fetchStreamPaging(page = page)

            val streamList = response.body()!!.streams
            if (streamList != null) {
                val nextPageNumber = page + 1
                LoadResult.Page(
                    data = streamList,
                    prevKey = null,
                    nextKey = nextPageNumber
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Stream>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}