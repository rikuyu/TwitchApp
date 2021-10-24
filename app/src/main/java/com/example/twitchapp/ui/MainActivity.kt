package com.example.twitchapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.twitchapp.R
import com.example.twitchapp.databinding.ActivityMainBinding
import com.example.twitchapp.util.UtilObject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            bottomNav.setupWithNavController(
                Navigation.findNavController(
                    this@MainActivity,
                    R.id.twichNavHostFragment
                )
            )
        }

        findNavController(R.id.twichNavHostFragment)
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.twitchPageFragment -> hideBottomNav()
                    else -> showBottomNav()
                }
            }
    }

    private fun showBottomNav() {
        binding.apply {
            UtilObject.visible(bottomNav)
            UtilObject.visible(bottomLine)
        }
    }

    private fun hideBottomNav() {
        binding.apply {
            UtilObject.invisible(bottomNav)
            UtilObject.invisible(bottomLine)
        }
    }
}