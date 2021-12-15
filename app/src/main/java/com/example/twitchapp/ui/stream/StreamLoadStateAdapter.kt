package com.example.twitchapp.ui.stream

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchapp.databinding.ItemStreamLoadStateBinding

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
                        progressbar.visibility = View.VISIBLE
                        errorMsg.visibility = View.INVISIBLE
                        buttonRetry.visibility = View.INVISIBLE
                    }
                }
                is LoadState.Error -> {
                    binding.apply {
                        progressbar.visibility = View.INVISIBLE
                        errorMsg.visibility = View.VISIBLE
                        buttonRetry.visibility = View.VISIBLE
                    }
                }
                is LoadState.NotLoading -> {
                    binding.apply {
                        progressbar.visibility = View.VISIBLE
                        errorMsg.visibility = View.INVISIBLE
                        buttonRetry.visibility = View.INVISIBLE
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