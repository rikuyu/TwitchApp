package com.example.twitchapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchapp.databinding.StreamLoadStateItemBinding

class StreamLoadStateAdapter(
    private val retry: () -> Unit
) :
    LoadStateAdapter<StreamLoadStateAdapter.StreamLoadStateViewHolder>() {

    inner class StreamLoadStateViewHolder(
        private val binding: StreamLoadStateItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retry: () -> Unit) {
            if (loadState is LoadState.Loading) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.INVISIBLE
            }

            if (loadState is LoadState.Error) {
                binding.buttonRetry.visibility = View.VISIBLE
            } else {
                binding.buttonRetry.visibility = View.INVISIBLE
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
        val binding = StreamLoadStateItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StreamLoadStateViewHolder(binding)
    }
}