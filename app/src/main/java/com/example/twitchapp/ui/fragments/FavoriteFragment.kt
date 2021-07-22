package com.example.twitchapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.adapter.FavoriteAdapter
import com.example.twitchapp.databinding.FragmentFavoriteBinding
import com.example.twitchapp.model.data.ProfileDialog
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.EditCustomDialog
import com.example.twitchapp.ui.MainActivity
import com.example.twitchapp.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var favoriteAdapter: FavoriteAdapter

    private var profileImageUri: String? = null
    private var profileName: String? = null
    lateinit var dataStore: SharedPreferences

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

        mainViewModel = (activity as MainActivity).mainViewModel
        mainViewModel.getFavoriteClips()

        setupRecyclerView()
        loadProfileData()

        mainViewModel.favoriteClips.observe(viewLifecycleOwner, Observer { favoriteList ->

            favoriteAdapter.submitList(favoriteList)
            binding.numLikes.text = "Likes ${favoriteList.size}"

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

        receiveDialogData()
        showEditProfileDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter(requireContext())
        binding.favoriteRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.favoriteRecyclerView.adapter = favoriteAdapter
    }

    private fun loadProfileData(){
        dataStore = this.requireActivity().getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        profileName = dataStore.getString("PROFILE_NAME", null)
        profileImageUri = dataStore.getString("PROFILE_IMAGE_URI", null)

        profileName?.let{
            binding.myName.text = profileName
        }
        profileImageUri?.let{
            binding.avatarImg.setImageURI(Uri.parse(profileImageUri))
        }
    }

    private fun receiveDialogData(){
        childFragmentManager.setFragmentResultListener("KEY_CLICKED", this) { key, bundle ->
            val newProfile = bundle.getParcelable<ProfileDialog>("NEW_PROFILE_KEY")

            binding.myName.text = newProfile!!.name
            profileImageUri = newProfile.avatarImageUri
            binding.avatarImg.setImageURI(null)
            binding.avatarImg.setImageURI(Uri.parse(profileImageUri))

            dataStore.edit().apply{
                putString("PROFILE_NAME", newProfile.name)
                putString("PROFILE_IMAGE_URI", newProfile.avatarImageUri)
            }.apply()
        }
    }

    private fun showEditProfileDialog(){
        binding.btnEdit.setOnClickListener {
            EditCustomDialog
                .Builder(this)
                .setName(binding.myName.text.toString())
                .setAvatarImage(profileImageUri)
                .setPositiveButton {
                    Toast.makeText(context, "User Name Changed", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton {
                    Toast.makeText(context, "canceled", Toast.LENGTH_SHORT).show()
                }
                .build()
                .show(childFragmentManager, EditCustomDialog::class.simpleName)
        }
    }
}