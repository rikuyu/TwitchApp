package com.example.twitchapp.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.adapter.ClipAdapter
import com.example.twitchapp.adapter.FavoriteAdapter
import com.example.twitchapp.databinding.FragmentClipBinding
import com.example.twitchapp.databinding.FragmentFavoriteBinding
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.MainActivity
import com.example.twitchapp.ui.MainViewModel


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var viewModel: MainViewModel

    private lateinit var favoriteAdapter: FavoriteAdapter
    private var favoList: List<Clip>? = null

    private var _binding: FragmentFavoriteBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).mainViewModel
        viewModel.getFavoriteClips()

        viewModel.dbClips.observe(viewLifecycleOwner, Observer {
            favoList = it
            binding.favoriteRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            favoriteAdapter = FavoriteAdapter(requireContext(), favoList)
            binding.favoriteRecyclerView.adapter = favoriteAdapter

            favoriteAdapter.setOnThumbnailClickListener(object :
                FavoriteAdapter.ShowFavoClip {
                override fun showFavoClip(url: String) {
                    val uri = Uri.parse(url)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            })
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}