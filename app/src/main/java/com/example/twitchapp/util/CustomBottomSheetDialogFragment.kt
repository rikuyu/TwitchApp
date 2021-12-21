package com.example.twitchapp.util

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twitchapp.databinding.CustomBottomSheetDialogBinding
import com.example.twitchapp.model.data.clipdata.Clip
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomBottomSheetDialogFragment : BottomSheetDialogFragment(), BottomSheetDialogListenr {

    private val chromeCustomTabsManager: ChromeCustomTabsManager = ChromeCustomTabsManager()
    private var clip: Clip? = null
    private var _binding: CustomBottomSheetDialogBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CustomBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.itemPlay.setOnClickListener {
            Log.d("AAAAAAAAA", "play")
            dismiss()
        }

        binding.itemFavorite.setOnClickListener {
            favorite { }
            Log.d("AAAAAAAAA", "favorite")
            dismiss()
        }

        binding.itemDelete.setOnClickListener {
            delete { }
            Log.d("AAAAAAAAA", "delete")
            dismiss()
        }

        binding.itemCopy.setOnClickListener {
            Log.d("AAAAAAAAA", "copy")
            dismiss()
        }

        binding.itemBack.setOnClickListener {
            Log.d("AAAAAAAAA", "back")
            dismiss()
            back()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun play(context: Context, url: String) {
        chromeCustomTabsManager.openChromeCustomTabs(context, url)
    }

    override fun favorite(addFavorite: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun delete(deleteFavorite: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun copy(url: String) {
        TODO("Not yet implemented")
    }

    override fun back() {
        dismiss()
    }

    companion object {
        fun newInstance(): CustomBottomSheetDialogFragment {
            val args = Bundle()
            val fragment = CustomBottomSheetDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}