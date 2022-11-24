package com.example.articlestest.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.fragment.app.viewModels
import com.example.articlestest.R
import com.example.articlestest.presentation.MainActivity
import com.example.articlestest.presentation.authorization.phone_check.AuthorizationPhoneFragment
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.registration.user_data.RegistrationUserDataFragment
import com.example.articlestest.presentation.theme.WhiteSmoke
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    val viewModel: SplashViewModel by viewModels()

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
                        viewModel.initEvent()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                NavDestination.AuthorizationPhone -> {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, AuthorizationPhoneFragment.newInstance())
                        .addToBackStack("authorization_phone")
                        .commit()
                }
                is NavDestination.AppMain -> {
                    val intent = Intent(this.context, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                is NavDestination.RegistrationUserData -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(R.id.container, RegistrationUserDataFragment.newInstance())
                        .commit()
                }
                else -> {}
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
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteSmoke),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo_big),
            contentDescription = null
        )
    }
}