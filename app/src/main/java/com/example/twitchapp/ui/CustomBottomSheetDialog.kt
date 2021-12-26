package com.example.twitchapp.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.twitchapp.databinding.CustomBottomSheetDialogBinding
import com.example.twitchapp.model.data.clipdata.Clip
import com.example.twitchapp.model.data.streamdata.Stream
import com.example.twitchapp.util.BottomSheetDialogListenr
import com.example.twitchapp.util.ChromeCustomTabsManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomBottomSheetDialog(
    private val favoriteClip: (clip: Clip) -> Unit,
    private val deleteClip: (clip: Clip) -> Unit
) : BottomSheetDialogFragment(), BottomSheetDialogListenr {

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

    override fun favorite(clip: Clip) {
        favoriteClip(clip)
    }

    override fun delete(clip: Clip) {
        deleteClip(clip)
    }

    override fun copy(url: String) {
        context?.let {
            val clipboard = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", url)
            clipboard.setPrimaryClip(clip)
        }
    }

    override fun back() {
        dismiss()
    }

    private fun setTapEvent() {
        binding.itemPlay.setOnClickListener {
            context?.let { context ->
                val url = clip?.url ?: stream?.channel?.url
                url?.let {
                    play(context, it)
                }
                if (url == null) {
                    Toast.makeText(context, "予期せぬエラー", Toast.LENGTH_SHORT).show()
                }
            }
            dismiss()
        }

        binding.itemFavorite.setOnClickListener {
            clip?.let {
                favorite(it)
                Toast.makeText(context, "いいね", Toast.LENGTH_SHORT).show()
            }
            if (clip == null) {
                Toast.makeText(context, "予期せぬエラー", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }

        binding.itemDelete.setOnClickListener {
            clip?.let {
                delete(it)
                Toast.makeText(context, "いいねから削除", Toast.LENGTH_SHORT).show()
            }
            if (clip == null) {
                Toast.makeText(context, "予期せぬエラー", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }

        binding.itemCopy.setOnClickListener {
            copy(clip?.url ?: stream?.channel?.url ?: "")
            Toast.makeText(context, "動画URLをコピー", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.itemBack.setOnClickListener {
            back()
        }
    }

    private fun getFragmentData() {
        parentFragmentManager.setFragmentResultListener(KEY, this) { _, bundle ->
            screenType = bundle.getParcelable<Parcelable>(SCREEN) as ScreenType
            val item = bundle.getParcelable<Parcelable>(ITEM)

            screenType?.let { type ->
                item?.let {
                    initDialogView(type, it)
                }
            }
        }
    }

    private fun initDialogView(screenType: ScreenType, item: Parcelable) {
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
    }
}