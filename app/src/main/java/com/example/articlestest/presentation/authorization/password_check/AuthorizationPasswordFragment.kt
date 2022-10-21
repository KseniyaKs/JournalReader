package com.example.articlestest.presentation.authorization.password_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.articlestest.R
import com.example.articlestest.huinya.base.BaseViewState
import com.example.articlestest.presentation.authorization.confirmcode_check.AuthorizationConfirmcodeFragment
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.view.LogoAndBack
import com.example.articlestest.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationPasswordFragment : Fragment() {

    companion object {
        private const val PHONE = "PHONE"

        fun newInstance(phone: String) = AuthorizationPasswordFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PHONE, phone)
            }
        }
    }

    private val viewModel: AuthorizationPasswordViewModel by viewModels()

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
                    AuthorizationPasswordScreen(
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
                is NavDestination.AuthorizationConfirmCode -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.container,
                            AuthorizationConfirmcodeFragment.newInstance(destination.phone)
                        )
                        .addToBackStack("authorization_confirm_code")
                        .commit()
                }
                NavDestination.AppMain -> {
                    //new activity
                }
                else -> {}
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthorizationPasswordScreen(
    viewModel: AuthorizationPasswordViewModel,
    phone: String
) {
    val context = LocalContext.current

    val password = remember { mutableStateOf("") } //testtest
    val keyboardController = LocalSoftwareKeyboardController.current

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LogoAndBack { viewModel.onBack() }

            Text(
                text = stringResource(id = R.string.enter_password),
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
            )

            Text(
                text = stringResource(id = R.string.password),
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                modifier = Modifier.padding(PaddingValues(top = 35.dp, bottom = 8.dp))
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Grey900,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Grey300,
                    unfocusedIndicatorColor = Grey300,
                    cursorColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                    fontSize = 20.sp,
                ),
                value = password.value,
                onValueChange = { password.value = it }
            )

            if (uiState is BaseViewState.Error) {
                IncorrectPassword(
                    forgotPasswordClick = {
                        viewModel.onNavigationEvent(
                            NavDestination.AuthorizationConfirmCode(phone)
                        )
                    }
                )
            }
        }


        Column(
            modifier = Modifier.weight(1f, false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(37.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Pink),
                onClick = {
                    viewModel.onTriggerEvent(
                        eventType = AuthorizationPasswordEvent.SignIn(
                            phone = phone,
                            password = password.value
                        )// testtest
                    )
                }
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

@Composable
fun IncorrectPassword(
    forgotPasswordClick: () -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.incorrect_password),
            fontSize = 12.sp,
            color = BrightRed,
            modifier = Modifier.padding(PaddingValues(top = 12.dp, bottom = 24.dp))
        )

        Text(
            text = stringResource(id = R.string.forgot_password),
            style = TextStyle(
                fontSize = 12.sp,
                color = HyperLinkBlue
            ),
            modifier = Modifier.clickable(onClick = {
                forgotPasswordClick()
            })
        )
    }

}