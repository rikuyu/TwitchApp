package com.example.twitchapp.ui

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.twitchapp.databinding.EditProfileDialogBinding
import com.example.twitchapp.model.data.ProfileDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCustomDialog : DialogFragment() {

    private var currentName: String = ""
    private var currentAvatarImageUri: String? = null
    private lateinit var binding: EditProfileDialogBinding

    // Galleryから選択した画像のURI
    private var uri: Uri? = null

    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }

    class Builder(private val fragment: Fragment) {
        private val bundle = Bundle()

        fun setName(name: String): Builder {
            return this.apply {
                bundle.putString("NAME_KEY", name)
            }
        }

        fun setAvatarImage(oldAvatarImageUri: String?): Builder {
            return this.apply {
                bundle.putString("IMAGE_KEY", oldAvatarImageUri)
            }
        }

        fun setPositiveButton(listener: () -> Unit): Builder {
            fragment.childFragmentManager
                .setFragmentResultListener(
                    "POSITIVE_BTN_KEY",
                    fragment.viewLifecycleOwner
                ) { _, _ ->
                    listener?.invoke()
                }
            return this
        }

        fun setNegativeButton(listener: () -> Unit): Builder {
            fragment.childFragmentManager
                .setFragmentResultListener(
                    "NEGATIVE_BTN_KEY",
                    fragment.viewLifecycleOwner
                ) { _, _ ->
                    listener?.invoke()
                }
            return this
        }

        // CustomDialog インスタンス生成＆設定した値を反映
        fun build(): EditCustomDialog {
            return EditCustomDialog().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        binding = EditProfileDialogBinding.inflate(requireActivity().layoutInflater)

        // 現在の名前をEditTextに表示するため
        arguments?.let {
            currentName = it.getString("NAME_KEY", "No Name")
            currentAvatarImageUri = it.getString("IMAGE_KEY")
        }

        // 現在のプロフィールをダイアログに反映
        binding.editMyName.setText(currentName)

        currentAvatarImageUri?.let { uriStr ->
            binding.editAvatarImage.setImageURI(null)
            binding.editAvatarImage.setImageURI(Uri.parse(uriStr))
        }

        binding.editAvatarImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, READ_REQUEST_CODE)
        }

        binding.btnOk.setOnClickListener {

            // OK ボタンが押されたときの関数を渡す
            setFragmentResult(
                "POSITIVE_BTN_KEY",
                bundleOf()
            )

            // 親のFavoriteFragmentにEditTextの値を渡す
            val newName = binding.editMyName.text.toString()
            val newAvatarImageUri = uri.toString()
            val newProfile = ProfileDialog(newName, newAvatarImageUri)

            setFragmentResult("KEY_CLICKED", bundleOf("NEW_PROFILE_KEY" to newProfile))
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
            setFragmentResult(
                "NEGATIVE_BTN_KEY",
                bundleOf()
            )
        }

        dialog.setContentView(binding.root)
        return dialog
    }

    // いったんCustomDialogに画像を適用する
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        resultData: Intent?
    ) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                uri = resultData.data
                if (Build.VERSION.SDK_INT < 28) {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        uri
                    )
                    binding.editAvatarImage.setImageBitmap(bitmap)
                } else {
                    val source = ImageDecoder.createSource(requireContext().contentResolver, uri!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    binding.editAvatarImage.setImageBitmap(bitmap)
                }
            }
        }
    }
}