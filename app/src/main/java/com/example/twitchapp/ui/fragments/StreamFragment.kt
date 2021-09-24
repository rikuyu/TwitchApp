package com.example.twitchapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.adapter.StreamAdapter
import com.example.twitchapp.databinding.FragmentStreamBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StreamFragment : Fragment(R.layout.fragment_stream) {

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
        binding.streamRecyclerView.adapter = streamAdapter
        binding.streamRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        lifecycleScope.launch {
            mainViewModel.streamFlow.collectLatest {
                streamAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}