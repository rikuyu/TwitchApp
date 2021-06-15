package com.example.twitchapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.adapter.FavoriteAdapter
import com.example.twitchapp.databinding.FragmentFavoriteBinding
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.MainActivity
import com.example.twitchapp.ui.MainViewModel


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var viewModel: MainViewModel

    private lateinit var favoriteAdapter: FavoriteAdapter

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

        setupRecyclerView()

        viewModel.favoriteClips.observe(viewLifecycleOwner, Observer { favoriteList ->

            favoriteAdapter.submitList(favoriteList)

            if(favoriteList.isEmpty()){
                binding.emptyMag.visibility = View.VISIBLE
            }else{
                binding.emptyMag.visibility = View.GONE
            }

            favoriteAdapter.setOnThumbnailClickListener(object :
                FavoriteAdapter.ShowFavoClip {
                override fun showFavoClip(url: String) {
                    val uri = Uri.parse(url)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            })

            favoriteAdapter.setOnDeleteBtnClickListener(object :
                FavoriteAdapter.DeleteItem {
                override fun deleteItem(clip: Clip) {
                    viewModel.deleteClip(clip)
                    //viewModel.getFavoriteClips()
                    favoriteAdapter.submitList(favoriteList)
                }
            }
            )
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView(){
        favoriteAdapter = FavoriteAdapter(requireContext())
        binding.favoriteRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.favoriteRecyclerView.adapter = favoriteAdapter
    }
}