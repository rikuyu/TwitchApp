package com.example.twitchapp.ui.myprofile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.databinding.FragmentMyProfileBinding
import com.example.twitchapp.model.data.NewProfileData
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.util.SharedPreferencesManager
import com.example.twitchapp.util.UtilObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : Fragment() {

    private lateinit var myProfileAdapter: MyProfileAdapter

    private var profileImageUri: String? = null
    private var _binding: FragmentMyProfileBinding? = null

    private val mainViewModel: MainViewModel by activityViewModels()
    private val sharedPreferencesManager: SharedPreferencesManager = SharedPreferencesManager()
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

        binding.btnEdit.setOnClickListener {
            EditCustomDialog
                .Builder()
                .setName(binding.myProfileName.text.toString())
                .setAvatarImage(profileImageUri)
                .build()
                .show(childFragmentManager, EditCustomDialog::class.simpleName)
        }
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
        context?.let {
            val profileName = sharedPreferencesManager.getProfileName(it)
            val profileImage = sharedPreferencesManager.getProfileImage(it)

            profileName?.let { name ->
                binding.myProfileName.text = name
            }
            profileImage?.let { uriString ->
                binding.myProfileImage.setImageURI(Uri.parse(uriString))
            }
        }
    }

    private fun receiveDialogData() {
        childFragmentManager.setFragmentResultListener(KEY_CLICKED, this) { _, bundle ->
            val newProfile = bundle.getParcelable<NewProfileData>(NEW_PROFILE_KEY)

            binding.myProfileName.text = newProfile?.newProfileName ?: "No Name"
            binding.myProfileImage.apply {
                setImageURI(null)
                if (newProfile != null) {
                    setImageURI(Uri.parse(newProfile.newProfileImage))
                } else {
                    setImageResource(R.drawable.no_image)
                }
            }

            context?.let {
                sharedPreferencesManager.apply {
                    saveProfileName(it, newProfile?.newProfileName ?: "No Name")
                    saveProfileImageUrl(it, newProfile?.newProfileImage ?: "")
                }
            }
        }
    }

    companion object {
        private const val KEY_CLICKED = "KEY_CLICKED"
        private const val NEW_PROFILE_KEY = "NEW_PROFILE_KEY"
    }
}