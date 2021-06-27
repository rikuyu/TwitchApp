package com.example.twitchapp.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.twitchapp.R
import com.example.twitchapp.databinding.EditProfileDialogBinding

class EditCustomDialog: DialogFragment() {

    private var name: String = ""

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
        val binding = EditProfileDialogBinding.inflate(requireActivity().layoutInflater)

        // 現在の名前をEditTextに表示するため
        arguments?.let{
            name = it.getString("NameKey", "No Name")
        }
        binding.editMyName.setText(name)

        binding.editAvatarImage.outlineProvider = clipOutlineProvider
        binding.editAvatarImage.clipToOutline = true

        binding.btnOk.setOnClickListener {
            // ダイアログは自動的に消えないので明示的に消す
            dismiss()

            setFragmentResult(
                "PositiveButtonKey",
                bundleOf()
            )
        }

        binding.btnCancel.setOnClickListener {
            // ダイアログは自動的に消えないので明示的に消す
            dismiss()

            setFragmentResult(
                "NegativeButtonKey",
                bundleOf()
            )
        }

        dialog.setContentView(binding.root)
        return dialog
    }

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
}