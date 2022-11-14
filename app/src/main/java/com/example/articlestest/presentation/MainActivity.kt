package com.example.articlestest.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.articlestest.R
import com.example.articlestest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val bottomNavigationView = binding?.navView

        bottomNavigationView?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.read_journal -> bottomNavigationView?.visibility = View.GONE
                R.id.article_content -> bottomNavigationView?.visibility = View.GONE
                R.id.article_comments -> bottomNavigationView?.visibility = View.GONE
                else -> bottomNavigationView?.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
