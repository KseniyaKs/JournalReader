package com.example.articlestest.presentation.main_app.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.articlestest.R
import com.example.articlestest.presentation.AuthorizationActivity
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.theme.BrightRed
import com.example.articlestest.presentation.theme.DarkGrey
import com.example.articlestest.presentation.view.ButtonMaxWidthWithText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

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
                    ProfileScreen(viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                NavDestination.AuthorizationPhone -> {
                    val intent = Intent(this.context, AuthorizationActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                else -> {}
            }
        }
    }
}

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.author_name),
            fontFamily = FontFamily(Font(R.font.poiret_one_regular_400)),
            fontSize = 30.sp,
            color = DarkGrey
        )

        Spacer(modifier = Modifier.height(24.dp))

        ButtonMaxWidthWithText(
            onClick = {},
            background = Color.White,
            text = stringResource(id = R.string.restore_subscription),
            textColor = Color.Black
        )

        ButtonMaxWidthWithText(
            onClick = {},
            background = Color.White,
            text = stringResource(id = R.string.unsubscribe),
            textColor = Color.Black
        )

        ButtonMaxWidthWithText(
            onClick = {},
            background = Color.White,
            text = stringResource(id = R.string.contact_support),
            textColor = Color.Black
        )

        ButtonMaxWidthWithText(
            onClick = {
                viewModel.onTriggerEvent(eventType = ProfileEvent.Logout)
            },
            background = BrightRed,
            text = stringResource(id = R.string.logout),
            textColor = Color.White
        )
    }
}