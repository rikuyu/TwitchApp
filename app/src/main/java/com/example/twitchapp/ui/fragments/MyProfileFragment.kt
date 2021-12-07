package com.example.twitchapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.ui.adapter.MyProfileAdapter
import com.example.twitchapp.databinding.FragmentMyProfileBinding
import com.example.twitchapp.model.data.NewProfileData
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.EditCustomDialog
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.util.UtilObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var myProfileAdapter: MyProfileAdapter
    private lateinit var dataStore: SharedPreferences
    private var profileImageUri: String? = null
    private var _binding: FragmentMyProfileBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadProfileData()

        mainViewModel.favoriteClips?.observe(viewLifecycleOwner, { favoriteList ->

            myProfileAdapter.submitList(favoriteList)
            binding.numLikes.text = getString(R.string.number_likes, favoriteList.size)

            if (favoriteList.isEmpty()) {
                UtilObject.visible(binding.emptyMsg)
            } else {
                UtilObject.invisible(binding.emptyMsg)
            }

            myProfileAdapter.setOnThumbnailClickListener(object :
                    MyProfileAdapter.ShowFavoClip {
                    override fun showFavoClip(url: String) {
                        val uri = Uri.parse(url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                })

            myProfileAdapter.setOnDeleteBtnClickListener(object :
                    MyProfileAdapter.DeleteItem {
                    override fun deleteItem(clip: Clip) {
                        mainViewModel.deleteClip(clip)
                        myProfileAdapter.submitList(favoriteList)
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
        myProfileAdapter = MyProfileAdapter(requireContext())
        binding.favoriteRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = myProfileAdapter
        }
    }

    private fun loadProfileData() {
        dataStore =
            this.requireActivity().getSharedPreferences(PROFILE_DATA_STORE, Context.MODE_PRIVATE)
        val profileName = dataStore.getString(STORED_PROFILE_NAME, null)
        profileImageUri = dataStore.getString(STORED_PROFILE_IMAGE_URI, null)

        profileName?.let {
            binding.myName.text = it
        }
        profileImageUri?.let {
            binding.avatarImg.setImageURI(Uri.parse(it))
        }
    }

    private fun receiveDialogData() {
        childFragmentManager.setFragmentResultListener(KEY_CLICKED, this) { key, bundle ->
            val newProfile = bundle.getParcelable<NewProfileData>(NEW_PROFILE_KEY)

            newProfile?.let {
                binding.myName.text = it.newProfileName
                profileImageUri = it.newProfileImage
                binding.avatarImg.apply {
                    setImageURI(null)
                    setImageURI(Uri.parse(profileImageUri))
                }
            }

            dataStore.edit().apply {
                putString(STORED_PROFILE_NAME, newProfile?.newProfileName)
                putString(STORED_PROFILE_IMAGE_URI, newProfile?.newProfileImage)
            }.apply()
        }
    }

    private fun showEditProfileDialog() {
        binding.btnEdit.setOnClickListener {
            EditCustomDialog
                .Builder()
                .setName(binding.myName.text.toString())
                .setAvatarImage(profileImageUri)
                .build()
                .show(childFragmentManager, EditCustomDialog::class.simpleName)
        }
    }

    companion object {
        private const val KEY_CLICKED = "KEY_CLICKED"
        private const val NEW_PROFILE_KEY = "NEW_PROFILE_KEY"
        private const val STORED_PROFILE_NAME = "STORED_PROFILE_NAME"
        private const val STORED_PROFILE_IMAGE_URI = "STORED_PROFILE_IMAGE_URI"
        private const val PROFILE_DATA_STORE = "PROFILE_DATA_STORE"
    }
}