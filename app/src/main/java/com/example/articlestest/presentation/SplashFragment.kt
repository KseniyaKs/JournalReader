package com.example.articlestest.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.Fragment
import com.example.articlestest.R
import com.example.articlestest.presentation.authorization.phone_check.AuthorizationPhoneFragment
import kotlinx.coroutines.delay


class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    SplashScreen {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, AuthorizationPhoneFragment.newInstance())
                            .commit()
                    }
                }
            }
        }
    }

}

@Composable
fun SplashScreen(onFinishedShowing: () -> Unit) {
    val currentOnFinishedShowing by rememberUpdatedState(onFinishedShowing)

    LaunchedEffect(Unit) {
        delay(1500)
        currentOnFinishedShowing()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("asdasd", "splash")
        Image(
            painter = painterResource(id = R.drawable.ic_logo_big),
            contentDescription = null
        )
    }
}


//    LaunchedEffect(null) {
//        delay(3000L)
//        navController.navigate("authorization_check_screen"){
//        }

//        navController.navigate("authorization_check_screen") {
//            popUpTo("splash_screen") {
//                inclusive = true
//            }
//        }
//    }