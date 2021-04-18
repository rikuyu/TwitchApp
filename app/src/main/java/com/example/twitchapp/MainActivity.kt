package com.example.twitchapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.twitchapp.fragment.GameFragment
import com.example.twitchapp.fragment.StreamFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var streamFragment: StreamFragment
    private lateinit var gameFragment: GameFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        streamFragment = StreamFragment()
        gameFragment = GameFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_conteiner, gameFragment)
        transaction.commit()

        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.stream -> {
                    switchFragment(streamFragment)
                    true
                }
                R.id.game -> {
                    switchFragment(gameFragment)
                    true
                }
                R.id.setting -> {
                    Toast.makeText(this, "aaaa", Toast.LENGTH_LONG).show()
                    true
                }
                else -> {
                    true
                }
            }
        }

    }

    fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_conteiner, fragment)
        transaction.commit()
    }
}