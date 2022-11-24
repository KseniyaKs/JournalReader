package com.example.articlestest.presentation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.articlestest.R
import com.example.articlestest.presentation.splash.SplashFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        supportFragmentManager.beginTransaction().add(R.id.container, SplashFragment.newInstance())
            .commit()
    }
}

