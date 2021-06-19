package com.example.twitchapp.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.fragment.app.DialogFragment
import com.example.twitchapp.R
import com.example.twitchapp.databinding.EditProfileDialogBinding

class EditCustomDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val binding = EditProfileDialogBinding.inflate(requireActivity().layoutInflater)

        binding.editAvatarImage.outlineProvider = clipOutlineProvider
        binding.editAvatarImage.clipToOutline = true

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