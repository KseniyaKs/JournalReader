package com.example.articlestest.presentation.registration.create_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.articlestest.R
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.registration.user_data.RegistrationUserDataFragment
import com.example.articlestest.presentation.theme.*
import com.example.articlestest.presentation.view.Back
import com.example.articlestest.presentation.view.ButtonMaxWidthWithText
import com.example.articlestest.presentation.view.ProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationPasswordFragment : Fragment() {

    companion object {
        private const val PHONE = "PHONE"
        fun newInstance(phone: String) = RegistrationPasswordFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PHONE, phone)
            }
        }
    }

    private val viewModel: RegistrationPasswordViewModel by viewModels()

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
                androidx.compose.material3.MaterialTheme {
                    RegistrationPasswordScreen(
                        viewModel,
                        phone = requireArguments().get("PHONE") as String
                    )
                }
            }
            isClickable = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.BackClick -> {
                    parentFragmentManager.popBackStack()
                }
                NavDestination.RegistrationUserData -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(R.id.container, RegistrationUserDataFragment.newInstance())
                        .addToBackStack("registration_user_data")
                        .commit()
                }
                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrationPasswordScreen(
    viewModel: RegistrationPasswordViewModel,
    phone: String
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val password = remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteSmoke)
            .padding(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (uiState is BaseViewState.Loading) {
            ProgressBar()
        }
        if (uiState !is BaseViewState.Loading) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Back { viewModel.onBack() }

                Text(
                    text = stringResource(id = R.string.password),
                    fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
                    modifier = Modifier.padding(PaddingValues(bottom = 8.dp))
                )

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
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
                        fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                        fontSize = 20.sp,
                    )
                )

                Text(
                    text = stringResource(id = R.string.password_help),
                    fontSize = 12.sp,
                    color = AlphaBlack,
                    fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
                    modifier = Modifier.padding(PaddingValues(top = 8.dp))
                )
            }
        }


        Column(
            modifier = Modifier.weight(1f, false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            ButtonMaxWidthWithText(
                background = Pink,
                text = stringResource(id = R.string.continue_button),
                textColor = Color.White,
                enabled = password.value.isNotEmpty(),
                onClick = {
                    viewModel.onTriggerEvent(
                        eventType = RegistrationPasswordEvent.SignUp(
                            phone = phone,
                            password = password.value
                        )
                    )
                }
            )
        }
    }
}