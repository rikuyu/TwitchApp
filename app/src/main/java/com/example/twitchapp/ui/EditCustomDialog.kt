package com.example.twitchapp.ui

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.twitchapp.databinding.EditProfileDialogBinding

class EditCustomDialog: DialogFragment() {

    private var name: String = ""
    private lateinit var binding: EditProfileDialogBinding

    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }

    class Builder(private val fragment: Fragment) {
        private val bundle = Bundle()

        fun setName(name: String): Builder {
            return this.apply {
                bundle.putString("NameKey", name)
            }
        }

        fun setPositiveButton(listener: () -> Unit): Builder {
            fragment.childFragmentManager
                .setFragmentResultListener(
                    "PositiveButtonKey",
                    fragment.viewLifecycleOwner
                ) { _, _ ->
                    listener?.invoke()
                }
            return this
        }

        fun setNegativeButton(listener: () -> Unit): Builder {
            fragment.childFragmentManager
                .setFragmentResultListener(
                    "NegativeButtonKey",
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
        arguments?.let{
            name = it.getString("NameKey", "No Name")
        }
        binding.editMyName.setText(name)

        binding.editAvatarImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, READ_REQUEST_CODE)
        }

        binding.btnOk.setOnClickListener {

            // OK ボタンが押されたときの関数を渡す
            setFragmentResult(
                "PositiveButtonKey",
                bundleOf()
            )

            // 親のFavoriteFragmentにEditTextの値を渡す
            val newName = binding.editMyName.text.toString()
            setFragmentResult("keyClicked", bundleOf("NewNameKey" to newName))

            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
            setFragmentResult(
                "NegativeButtonKey",
                bundleOf()
            )
        }

        dialog.setContentView(binding.root)
        return dialog
    }

    // いったんCustomDialogに画像を適用する
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        resultData: Intent?
    ) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var uri: Uri? = null
            if (resultData != null) {
                uri = resultData.data
                if(Build.VERSION.SDK_INT < 28) {
                    val bitmap =  MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        uri
                    )
                    Log.d("bitmap", "画像をセット")
                    binding.editAvatarImage.setImageBitmap(bitmap)
                } else {
                    val source = ImageDecoder.createSource(requireContext().contentResolver, uri!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    Log.d("bitmap", "画像をセット")
                    binding.editAvatarImage.setImageBitmap(bitmap)
                }
            }
        }
    }
}