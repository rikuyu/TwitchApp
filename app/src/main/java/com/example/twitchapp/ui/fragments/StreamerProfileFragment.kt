package com.example.twitchapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twitchapp.R
import com.example.twitchapp.databinding.FragmentStreamerProfileBinding
import com.example.twitchapp.ui.MainViewModel


class StreamerProfileFragment : Fragment(R.layout.fragment_streamer_profile) {

    lateinit var viewModel: MainViewModel
    private var _binding: FragmentStreamerProfileBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStreamerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}