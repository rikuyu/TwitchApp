package com.example.twitchapp.ui.stream

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.databinding.FragmentStreamBinding
import com.example.twitchapp.ui.ItemClickListener
import com.example.twitchapp.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StreamFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var streamAdapter: StreamAdapter
    private var _binding: FragmentStreamBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStreamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        streamAdapter = StreamAdapter(requireContext())

        binding.streamRecyclerView.apply {
            adapter =
                streamAdapter.withLoadStateFooter(StreamLoadStateAdapter(streamAdapter::retry))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        streamAdapter.setListener(
            object : ItemClickListener {
                override fun thumbnailClickListener(url: String) {
                    val uri = Uri.parse(url)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }
        )

        lifecycleScope.launch {
            mainViewModel.streamFlow.collectLatest {
                streamAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            streamAdapter.loadStateFlow.collectLatest { state ->
                when (state.refresh) {
                    is LoadState.Loading -> {
                        binding.apply {
                            progressbar.visibility = View.VISIBLE
                            pagingErrorMsg.visibility = View.INVISIBLE
                        }
                    }
                    is LoadState.Error -> {
                        binding.apply {
                            progressbar.visibility = View.INVISIBLE
                            pagingErrorMsg.visibility = View.VISIBLE
                        }
                    }
                    is LoadState.NotLoading -> {
                        binding.apply {
                            progressbar.visibility = View.INVISIBLE
                            pagingErrorMsg.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}