package com.example.twitchapp.ui.myprofile

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.twitchapp.R
import com.example.twitchapp.databinding.EditProfileDialogBinding
import com.example.twitchapp.model.data.NewProfileData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCustomDialog private constructor() : DialogFragment() {

    private var currentName: String? = null
    private var currentProfileImageUri: String? = null
    private var newImageUri: Uri? = null
    private var _binding: EditProfileDialogBinding? = null
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

        fun build(): EditCustomDialog {
            return EditCustomDialog().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        _binding = EditProfileDialogBinding.inflate(requireActivity().layoutInflater)

        loadProfileData()
        setNewProfileImage()

        binding.btnOk.setOnClickListener {
            val newProfileName = binding.currentName.text.toString()
            when {
                newProfileName.isEmpty() -> {
                    binding.currentName.error = getString(R.string.dialog_error_empty_label)
                }
                newProfileName.length > 10 -> {
                    binding.currentName.error = getString(R.string.dialog_error_over_label, 10)
                }
                else -> {
                    val newProfileImageUri = newImageUri.toString()
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
        binding.currentName.setText(currentName)
        currentProfileImageUri?.let {
            binding.editProfileImage.apply {
                setImageURI(null)
                setImageURI(Uri.parse(it))
            }
        }
    }

    private fun setNewProfileImage() {
        currentProfileImageUri?.let {
            newImageUri = Uri.parse(it)
        }
        val launcher = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            it?.let { newImageUri = it }
            binding.editProfileImage.setImageURI(newImageUri)
        }
        binding.editProfileImage.setOnClickListener {
            launcher.launch(arrayOf("image/*"))
        }
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