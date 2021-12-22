package com.example.twitchapp.ui.stream

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.databinding.FragmentStreamBinding
import com.example.twitchapp.ui.ItemClickListener
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.ui.ScreenType
import com.example.twitchapp.util.ChromeCustomTabsManager
import com.example.twitchapp.util.CustomBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StreamFragment : Fragment() {

    private lateinit var streamAdapter: StreamAdapter
    private var _binding: FragmentStreamBinding? = null

    private val mainViewModel: MainViewModel by activityViewModels()
    private val chromeCustomTabsManager: ChromeCustomTabsManager = ChromeCustomTabsManager()

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStreamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            streamAdapter = StreamAdapter(it)
        }

        binding.streamRecyclerView.apply {
            adapter =
                streamAdapter.withLoadStateFooter(StreamLoadStateAdapter(streamAdapter::retry))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        streamAdapter.setListener(
            object : ItemClickListener {
                override fun thumbnailClickListener(url: String) {
                    context?.let {
                        chromeCustomTabsManager.openChromeCustomTabs(it, url)
                    }
                }

                override fun <T> longClickListener(item: T, screen: ScreenType) {
                    setFragmentResult(
                        CUSTOM_DIALOG_KEY,
                        bundleOf(ITEM_KEY to item, SCREEN_KEY to screen)
                    )
                    CustomBottomSheetDialog.newInstance(
                        mainViewModel::insertGetClip,
                        mainViewModel::deleteClip
                    ).show(parentFragmentManager, "")
                }
            }
        )

        lifecycleScope.launch {
            mainViewModel.streamFlow.collectLatest {
                streamAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            streamAdapter.loadStateFlow.collectLatest { state ->
                when (state.refresh) {
                    is LoadState.Loading -> {
                        binding.apply {
                            progressbar.visibility = View.VISIBLE
                            pagingErrorMsg.visibility = View.INVISIBLE
                        }
                    }
                    is LoadState.Error -> {
                        binding.apply {
                            progressbar.visibility = View.INVISIBLE
                            pagingErrorMsg.visibility = View.VISIBLE
                        }
                    }
                    is LoadState.NotLoading -> {
                        binding.apply {
                            progressbar.visibility = View.INVISIBLE
                            pagingErrorMsg.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val CUSTOM_DIALOG_KEY = "custom_bottom_dialog_key"
        private const val ITEM_KEY = "item_key"
        private const val SCREEN_KEY = "screen_type"
    }
}