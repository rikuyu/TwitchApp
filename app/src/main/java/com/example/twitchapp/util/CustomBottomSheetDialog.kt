package com.example.twitchapp.util

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.twitchapp.databinding.CustomBottomSheetDialogBinding
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.streamdata.Stream
import com.example.twitchapp.ui.ScreenType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable

class CustomBottomSheetDialog : BottomSheetDialogFragment(), BottomSheetDialogListenr {

    private val chromeCustomTabsManager: ChromeCustomTabsManager = ChromeCustomTabsManager()

    private var screenType: ScreenType? = null
    private var stream: Stream? = null
    private var clip: Clip? = null
    private var _binding: CustomBottomSheetDialogBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CustomBottomSheetDialogBinding.inflate(requireActivity().layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTapEvent()
        getFragmentData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stream = null
        clip = null
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

    private fun setTapEvent() {
        binding.itemPlay.setOnClickListener {
            dismiss()
        }

        binding.itemFavorite.setOnClickListener {
            favorite { }
            dismiss()
        }

        binding.itemDelete.setOnClickListener {
            delete { }
            dismiss()
        }

        binding.itemCopy.setOnClickListener {
            dismiss()
        }

        binding.itemBack.setOnClickListener {
            dismiss()
            back()
        }
    }

    private fun getFragmentData() {
        parentFragmentManager.setFragmentResultListener(KEY, this) { _, bundle ->
            screenType = bundle.getSerializable(SCREEN) as ScreenType
            val item = bundle.getSerializable(ITEM)

            screenType?.let { type ->
                item?.let {
                    initDialogView(type, it)
                }
            }
        }
    }

    private fun initDialogView(screenType: ScreenType, item: Serializable) {
        when (screenType) {
            ScreenType.STREAM -> {
                stream = item as Stream
                binding.apply {
                    itemPlay.visibility = View.VISIBLE
                    line1.visibility = View.VISIBLE
                    itemCopy.visibility = View.VISIBLE
                    line4.visibility = View.VISIBLE
                }
            }
            ScreenType.CLIP -> {
                clip = item as Clip
                binding.apply {
                    itemPlay.visibility = View.VISIBLE
                    line1.visibility = View.VISIBLE
                    itemFavorite.visibility = View.VISIBLE
                    line2.visibility = View.VISIBLE
                    itemCopy.visibility = View.VISIBLE
                    line4.visibility = View.VISIBLE
                }
            }
            ScreenType.FAVORITE -> {
                clip = item as Clip
                binding.apply {
                    itemPlay.visibility = View.VISIBLE
                    line1.visibility = View.VISIBLE
                    itemDelete.visibility = View.VISIBLE
                    line3.visibility = View.VISIBLE
                    itemCopy.visibility = View.VISIBLE
                    line4.visibility = View.VISIBLE
                }
            }
        }
    }

    companion object {
        private const val KEY = "custom_bottom_dialog_key"
        private const val ITEM = "item_key"
        private const val SCREEN = "screen_type"

        fun newInstance(): CustomBottomSheetDialog {
            return CustomBottomSheetDialog()
        }
    }
}