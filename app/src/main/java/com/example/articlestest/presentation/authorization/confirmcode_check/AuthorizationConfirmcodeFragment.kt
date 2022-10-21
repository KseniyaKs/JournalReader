package com.example.articlestest.presentation.authorization.confirmcode_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
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
import com.example.articlestest.huinya.base.BaseViewState
import com.example.articlestest.huinya.base.presentation.view.Countdown
import com.example.articlestest.presentation.authorization.new_password.AuthorizationNewPasswordFragment
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.view.IncorrectCode
import com.example.articlestest.presentation.view.LogoAndBack
import com.example.articlestest.presentation.view.OTPTextFields
import com.example.articlestest.ui.theme.Pink
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationConfirmcodeFragment : Fragment() {

    companion object {
        private const val PHONE = "PHONE"

        fun newInstance(phone: String) = AuthorizationConfirmcodeFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PHONE, phone)
            }
        }
    }

    private val viewModel: AuthorizationConfirmCodeViewModel by viewModels()

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
                    AuthorizationConfirmCodeScreen(
                        viewModel,
                        phone = requireArguments().get("PHONE") as String
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.BackClick -> {
                    parentFragmentManager.popBackStack()
                }
                is NavDestination.AuthorizationNewPassword -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.container,
                            AuthorizationNewPasswordFragment.newInstance(destination.phone)
                        )
                        .addToBackStack("authorization_confirm_code")
                        .commit()
                }

                else -> {}
            }
        }
    }

}

@Composable
fun AuthorizationConfirmCodeScreen(
    viewModel: AuthorizationConfirmCodeViewModel,
    phone: String
) {

    val max = 60

    val uiState by viewModel.uiState.collectAsState()
    val code = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.onTriggerEvent(
            eventType = AuthorizationConfirmCodeEvent.SendConfirmCode(
                phone = phone
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            LogoAndBack { viewModel.onBack() }

            Text(
                text = stringResource(id = R.string.send_code),
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
            )

            Text(
                text = stringResource(id = R.string.confirmation_code),
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                modifier = Modifier.padding(PaddingValues(top = 35.dp, bottom = 8.dp))
            )

            OTPTextFields(length = 4) { code.value = it }

            if (uiState is BaseViewState.Error) {
                IncorrectCode()
            }
        }

        Column(
            modifier = Modifier.weight(1f, false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Countdown(
                max = max,
                onResendConfirmCode = {
                    viewModel.onTriggerEvent(
                        AuthorizationConfirmCodeEvent.SendConfirmCode(
                            phone
                        )
                    )
                }
            )

            Button(
                onClick = {
                    viewModel.onTriggerEvent(
                        eventType = AuthorizationConfirmCodeEvent.CheckConfirmCode(
                            phone = phone,
                            code = code.value
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(37.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Pink)
            ) {
                Text(
                    text = stringResource(id = R.string.continue_button),
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }
    }
}
