package com.example.twitchapp.ui.myprofile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchapp.R
import com.example.twitchapp.databinding.FragmentMyProfileBinding
import com.example.twitchapp.model.data.Games
import com.example.twitchapp.model.data.NewProfileData
import com.example.twitchapp.ui.CustomBottomSheetDialog
import com.example.twitchapp.ui.ItemClickListener
import com.example.twitchapp.ui.MainViewModel
import com.example.twitchapp.ui.ScreenType
import com.example.twitchapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyProfileFragment : Fragment() {

    private lateinit var myProfileAdapter: MyProfileAdapter

    private var profileImageString: String? = null
    private var _binding: FragmentMyProfileBinding? = null

    private val mainViewModel: MainViewModel by activityViewModels()
    private val sharedPreferencesManager: SharedPreferencesManager = SharedPreferencesManager()
    private val chromeCustomTabsManager: ChromeCustomTabsManager = ChromeCustomTabsManager()
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadProfileData()
        loadFilterGameData(requireContext())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.favoriteList.collect {
                    when (it) {
                        is Status.Success -> {
                            if (it.data.isNotEmpty()) {
                                binding.emptyMsg.visibility = View.GONE
                                binding.favoriteRecyclerView.visibility = View.VISIBLE
                                myProfileAdapter.submitList(it.data)
                                binding.numLikes.text = it.data.size.toString()
                            } else {
                                binding.emptyMsg.visibility = View.VISIBLE
                                binding.favoriteRecyclerView.visibility = View.GONE
                            }
                        }
                        is Status.Error -> {
                            Toast.makeText(requireContext(), it.throwable.message, Toast.LENGTH_SHORT).show()
                        }
                        is Status.Loading -> { }
                    }
                }
            }
        }

        receiveProfileEditDialogData()

        binding.btnEdit.setOnClickListener {
            ProfileEditCustomDialog
                .Builder()
                .setName(binding.myProfileName.text.toString())
                .setAvatarImage(profileImageString)
                .build()
                .show(childFragmentManager, ProfileEditCustomDialog::class.simpleName)
        }

        binding.btnFilter.setOnClickListener {
            FilterDialog().show(childFragmentManager, "")
        }

        mainViewModel.filterGame.observe(viewLifecycleOwner, { game ->
            if (game == null) {
                binding.filterGameImage.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.game_icon)
                )
            }
            setImageAndSaveGame(requireContext(), game.title)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        myProfileAdapter = MyProfileAdapter(requireContext())
        binding.favoriteRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = myProfileAdapter
        }
        context?.let {
            myProfileAdapter.setListener(object :
                    ItemClickListener {
                    override fun thumbnailClickListener(url: String) {
                        chromeCustomTabsManager.openChromeCustomTabs(it, url)
                    }

                    override fun <T> longClickListener(
                        item: T,
                        screen: ScreenType
                    ) {
                        setFragmentResult(
                            CUSTOM_DIALOG_KEY,
                            bundleOf(ITEM_KEY to item, SCREEN_KEY to screen)
                        )
                        CustomBottomSheetDialog(
                            mainViewModel::insertGetClip,
                            mainViewModel::deleteClip
                        ).show(parentFragmentManager, "")
                    }

                    override fun <T> menuClickListener(item: T, screen: ScreenType) {
                        setFragmentResult(
                            CUSTOM_DIALOG_KEY,
                            bundleOf(ITEM_KEY to item, SCREEN_KEY to screen)
                        )
                        CustomBottomSheetDialog(
                            mainViewModel::insertGetClip,
                            mainViewModel::deleteClip
                        ).show(parentFragmentManager, "")
                    }
                })
        }
    }

    private fun loadProfileData() {
        val profileName = sharedPreferencesManager.getProfileName(requireContext())
        val profileImage = sharedPreferencesManager.getProfileImage(requireContext())

        profileName?.let { name ->
            binding.myProfileName.text = name
        }
        profileImage?.let { uriString ->
            val bitmap = decodeBitmapFromBase64(uriString)
            if (bitmap == null) {
                binding.myProfileImage.setImageResource(R.drawable.no_profile_image)
            } else {
                binding.myProfileImage.setImageBitmap(bitmap)
            }
            profileImageString = uriString
        }
    }

    private fun receiveProfileEditDialogData() {
        childFragmentManager.setFragmentResultListener(KEY_CLICKED, this) { _, bundle ->
            val newProfile = bundle.getParcelable<NewProfileData>(NEW_PROFILE_KEY)

            binding.myProfileName.text = newProfile?.newProfileName ?: "No Name"
            binding.myProfileImage.apply {
                setImageURI(null)
                if (newProfile != null) {
                    val bitmap = decodeBitmapFromBase64(newProfile.newProfileImage)
                    if (bitmap == null) {
                        binding.myProfileImage.setImageResource(R.drawable.no_profile_image)
                    } else {
                        binding.myProfileImage.setImageBitmap(bitmap)
                    }
                    profileImageString = newProfile.newProfileImage
                } else {
                    setImageResource(R.drawable.no_profile_image)
                }
            }

            sharedPreferencesManager.apply {
                saveProfileName(requireContext(), newProfile?.newProfileName ?: "No Name")
                saveProfileImageUrl(requireContext(), newProfile?.newProfileImage ?: "")
            }
        }
    }

    private fun setImageAndSaveGame(context: Context, games: String) {
        binding.filterGameImage.setImageDrawable(
            getGameImage(context, games)
        )
        sharedPreferencesManager.saveFilterGame(context, games)
    }

    private fun loadFilterGameData(context: Context) {
        binding.filterGameImage.apply {
            val game = sharedPreferencesManager.getFilterGame(context)
            game?.let {
                if (game != Games.ALL.title) {
                    mainViewModel.getSpecificFavoriteGame(it)
                } else {
                    mainViewModel.getFavoriteGame()
                }
                setImageDrawable(getGameImage(context, it))
                mainViewModel.filterGame.value = when (game) {
                    Games.PUBG_MOBILE.title -> Games.PUBG_MOBILE
                    Games.APEX_LEGENDS.title -> Games.APEX_LEGENDS
                    Games.AMONG_US.title -> Games.AMONG_US
                    Games.GENSHIN.title -> Games.GENSHIN
                    Games.FORTNITE.title -> Games.FORTNITE
                    Games.MINECRAFT.title -> Games.MINECRAFT
                    Games.CALL_OF_DUTY.title -> Games.CALL_OF_DUTY
                    Games.LEAGUE_OF_LEGENDS.title -> Games.LEAGUE_OF_LEGENDS
                    Games.ALL.title -> Games.ALL
                    else -> Games.ALL
                }
            }
        }
    }

    companion object {
        private const val KEY_CLICKED = "KEY_CLICKED"
        private const val NEW_PROFILE_KEY = "NEW_PROFILE_KEY"
        private const val CUSTOM_DIALOG_KEY = "custom_bottom_dialog_key"
        private const val ITEM_KEY = "item_key"
        private const val SCREEN_KEY = "screen_type"
    }
}