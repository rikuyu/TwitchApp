package com.example.twitchapp.ui.myprofile

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.twitchapp.R
import com.example.twitchapp.databinding.EditProfileDialogBinding
import com.example.twitchapp.model.data.NewProfileData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditCustomDialog private constructor() : DialogFragment() {

    private var currentName: String? = null
    private var currentProfileImageUri: String? = null
    private var newImageUriStringFromGallery: String? = null
    private var _binding: EditProfileDialogBinding? = null

    private lateinit var launcher: ActivityResultLauncher<Intent>

    private val binding
        get() = _binding!!

    class Builder {
        private val bundle = Bundle()

        fun setName(name: String): Builder {
            return this.apply {
                bundle.putString(NAME_KEY, name)
            }
        }

        fun setAvatarImage(oldAvatarImageUri: String?): Builder {
            return this.apply {
                oldAvatarImageUri?.let {
                    bundle.putString(IMAGE_KEY, oldAvatarImageUri)
                }
            }
        }

        fun build(): ProfileEditCustomDialog {
            return ProfileEditCustomDialog().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        _binding = EditProfileDialogBinding.inflate(requireActivity().layoutInflater)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val imageUri = result.data?.data
                    if (imageUri != null) {
                        newImageUriStringFromGallery = imageUri.toString()
                        binding.editProfileImage.setImageURI(imageUri)
                        currentProfileImageUri = newImageUriStringFromGallery
                    } else {
                        if (currentProfileImageUri != null) {
                            binding.editProfileImage.setImageURI(Uri.parse(currentProfileImageUri))
                        } else {
                            binding.editProfileImage.setImageResource(R.drawable.no_profile_image)
                        }
                    }
                }
            }

        loadProfileData()

        binding.editProfileImage.setOnClickListener {
            setNewProfileImage()
        }

        binding.btnOk.setOnClickListener {
            val newProfileName = binding.dialogProfileName.text.toString()
            when {
                newProfileName.isEmpty() -> {
                    binding.dialogProfileName.error = getString(R.string.dialog_error_empty_label)
                }
                newProfileName.length > 10 -> {
                    binding.dialogProfileName.error =
                        getString(R.string.dialog_error_over_label, 10)
                }
                else -> {
                    val newProfileImageUri = currentProfileImageUri.toString()
                    val newProfile = NewProfileData(newProfileName, newProfileImageUri)
                    setFragmentResult(KEY_CLICKED, bundleOf(NEW_PROFILE_KEY to newProfile))
                    dismiss()
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        dialog.setContentView(binding.root)
        return dialog
    }

    private fun loadProfileData() {
        arguments?.let {
            currentName = it.getString(NAME_KEY, "No Name")
            currentProfileImageUri = it.getString(IMAGE_KEY)
        }
        binding.dialogProfileName.setText(currentName)
        currentProfileImageUri?.let {
            binding.editProfileImage.apply {
                setImageURI(null)
                setImageURI(Uri.parse(it))
            }
        }
    }

    private fun setNewProfileImage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        launcher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val NAME_KEY = "NAME_KEY"
        private const val IMAGE_KEY = "IMAGE_KEY"
        private const val KEY_CLICKED = "KEY_CLICKED"
        private const val NEW_PROFILE_KEY = "NEW_PROFILE_KEY"
    }
}