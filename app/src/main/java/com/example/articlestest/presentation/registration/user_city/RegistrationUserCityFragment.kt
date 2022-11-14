package com.example.articlestest.presentation.registration.user_city

import android.content.Intent
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.articlestest.R
import com.example.articlestest.presentation.MainActivity
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.theme.Grey300
import com.example.articlestest.presentation.theme.Grey900
import com.example.articlestest.presentation.theme.Pink
import com.example.articlestest.presentation.view.Back
import com.example.articlestest.presentation.view.ButtonMaxWidthWithText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationUserCityFragment : Fragment() {

    companion object {
        private const val NAME = "NAME"
        private const val SURNAME = "SURNAME"
        private const val PATRONYMIC = "PATRONYMIC"
        private const val EMAIL = "EMAIL"

        fun newInstance(name: String, surname: String, patronymic: String, email: String) =
            RegistrationUserCityFragment().apply {
                arguments = Bundle()
                    .apply { putSerializable(NAME, name) }
                    .apply { putSerializable(SURNAME, surname) }
                    .apply { putSerializable(PATRONYMIC, patronymic) }
                    .apply { putSerializable(EMAIL, email) }
            }
    }

    val viewModel: RegistrationUserCityViewModel by viewModels()

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
                    RegistrationUserCityScreen(
                        viewModel,
                        name = requireArguments().get("NAME") as String,
                        surname = requireArguments().get("SURNAME") as String,
                        patronymic = requireArguments().get("PATRONYMIC") as String,
                        email = requireArguments().get("EMAIL") as String
                    )
                }
            }
            isClickable = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.BackClick -> {
                    parentFragmentManager.popBackStack()
                }
                is NavDestination.AppMain -> {
                    val intent = Intent(this.context, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                else -> {}
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrationUserCityScreen(
    viewModel: RegistrationUserCityViewModel,
    name: String,
    surname: String,
    patronymic: String,
    email: String
) {
    val city = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.onTriggerEvent(
            eventType = RegistrationUserCityEvent.GetCity
        )
    }

    val cityState by viewModel.cityState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Back { viewModel.onBack() }

            Text(
                text = stringResource(id = R.string.your_city),
                fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(PaddingValues(bottom = 8.dp))
            )

            OutlinedTextField(
                value = city.value,
                onValueChange = { city.value = it },
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
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                    fontSize = 20.sp,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )
        }


        Column(
            modifier = Modifier.weight(1f, false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            ButtonMaxWidthWithText(
                background = Pink,
                text = stringResource(id = R.string.done),
                textColor = Color.White,
                enabled = city.value.isNotEmpty(),
                onClick = {
                    viewModel.onTriggerEvent(
                        eventType = RegistrationUserCityEvent.CreateUserInfo(
                            name = name,
                            surname = surname,
                            patronymic = patronymic,
                            email = email,
                            city = city.value
                        )
                    )
                }
            )
        }
    }
}