package com.example.twitchapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.twitchapp.R
import com.example.twitchapp.TwitchApplication
import com.example.twitchapp.databinding.ActivityMainBinding
import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.model.repository.TwitchRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val mainViewModel: MainViewModel
            by lazy {
                ViewModelProvider(
                    this, MainViewModelFactory(
                        application, TwitchRepository(
                            TwitchDatabase.getInstance(this)
                        )
                    )
                ).get(MainViewModel::class.java)
            }

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