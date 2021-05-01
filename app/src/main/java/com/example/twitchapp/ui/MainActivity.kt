package com.example.twitchapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.twitchapp.R
import com.example.twitchapp.databinding.ActivityMainBinding
import com.example.twitchapp.db.TwitchDatabase
import com.example.twitchapp.model.repository.TwitchRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = TwitchRepository(TwitchDatabase.getInstance(this))
        val viewModelFactory = MainViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

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