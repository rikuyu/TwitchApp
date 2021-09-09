package com.example.twitchapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.twitchapp.R
import com.example.twitchapp.databinding.ActivityMainBinding
import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.model.api.TwitchApi
import com.example.twitchapp.model.repository.TwitchRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

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
    }
}