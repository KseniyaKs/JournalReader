package com.example.articlestest.presentation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.articlestest.R
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

//        setContent {
//            val systemUiController = rememberSystemUiController()
//            val useDarkIcons = MaterialTheme.colors.isLight
//            SideEffect {
//                systemUiController.setSystemBarsColor(
//                    Color.White,
//                    darkIcons = useDarkIcons,
//                )
//            }
//
//
//            ArticlesTestTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                ) {
//                    MainScreenContent()
//                }
//            }
//
//        }

