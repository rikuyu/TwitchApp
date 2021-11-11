package com.example.twitchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchapp.databinding.ItemStreamLoadStateBinding
import com.example.twitchapp.util.UtilObject

class StreamLoadStateAdapter(
    private val retry: () -> Unit
) :
    LoadStateAdapter<StreamLoadStateAdapter.StreamLoadStateViewHolder>() {

    inner class StreamLoadStateViewHolder(
        private val binding: ItemStreamLoadStateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retry: () -> Unit) {
            when (loadState) {
                is LoadState.Loading -> {
                    binding.apply {
                        UtilObject.visible(progressbar)
                        UtilObject.invisible(errorMsg)
                        UtilObject.invisible(buttonRetry)
                    }
                }
                is LoadState.Error -> {
                    binding.apply {
                        UtilObject.invisible(progressbar)
                        UtilObject.visible(errorMsg)
                        UtilObject.visible(buttonRetry)
                    }
                }
                is LoadState.NotLoading -> {
                    binding.apply {
                        UtilObject.visible(progressbar)
                        UtilObject.invisible(errorMsg)
                        UtilObject.invisible(buttonRetry)
                    }
                }
            }
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: StreamLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): StreamLoadStateViewHolder {
        val binding = ItemStreamLoadStateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StreamLoadStateViewHolder(binding)
    }
}