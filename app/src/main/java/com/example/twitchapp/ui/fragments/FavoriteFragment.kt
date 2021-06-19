package com.example.twitchapp.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.adapter.FavoriteAdapter
import com.example.twitchapp.databinding.FragmentFavoriteBinding
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.MainActivity
import com.example.twitchapp.ui.MainViewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var mainViewModel: MainViewModel

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

        // 左上のゲームアイコンを円形にする
        binding.avatarImg.outlineProvider = clipOutlineProvider
        binding.avatarImg.clipToOutline = true

        mainViewModel = (activity as MainActivity).mainViewModel
        mainViewModel.getFavoriteClips()

        setupRecyclerView()

        mainViewModel.favoriteClips.observe(viewLifecycleOwner, Observer { favoriteList ->

            favoriteAdapter.submitList(favoriteList)

            if (favoriteList.isEmpty()) {
                binding.emptyMag.visibility = View.VISIBLE
            } else {
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
                    mainViewModel.deleteClip(clip)
                    favoriteAdapter.submitList(favoriteList)
                }
            }
            )
        })

        binding.btnEdit.setOnClickListener {
            EditCustomDialog().show(childFragmentManager, EditCustomDialog::class.simpleName)
            Toast.makeText(context, EditCustomDialog::class.simpleName, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 左上のゲームアイコンを円形にする
    private val clipOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setOval(
                0,
                0,
                view.width,
                view.height
            )
        }
    }

    private fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.edit_profile_dialog, null)

        val customDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter(requireContext())
        binding.favoriteRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.favoriteRecyclerView.adapter = favoriteAdapter
    }
}