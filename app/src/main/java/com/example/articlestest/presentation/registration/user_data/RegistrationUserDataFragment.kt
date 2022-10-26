package com.example.articlestest.presentation.registration.user_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.registration.user_city.RegistrationUserCityFragment
import com.example.articlestest.presentation.theme.Grey300
import com.example.articlestest.presentation.theme.Grey900
import com.example.articlestest.presentation.theme.GreyBlue
import com.example.articlestest.presentation.theme.Pink
import com.example.articlestest.presentation.view.Back
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationUserDataFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationUserDataFragment()
    }

    val viewModel: RegistrationUserDataViewModel by viewModels()

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
                    RegistrationUserDataScreen(viewModel)
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
                is NavDestination.RegistrationUserCity -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.container,
                            RegistrationUserCityFragment.newInstance(
                                name = destination.name,
                                surname = destination.surname,
                                patronymic = destination.patronymic,
                                email = destination.email
                            )
                        )
                        .addToBackStack("registration_user_city")
                        .commit()
                }
                else -> {}
            }
        }
    }
}


@Composable
fun RegistrationUserDataScreen(
    viewModel: RegistrationUserDataViewModel
) {

    var name = ""
    var surname = ""
    var patronymic = ""
    var email = ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Back { viewModel.onBack() }

            Text(
                text = stringResource(id = R.string.registration_help),
                fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(PaddingValues(bottom = 43.dp))
            )

            UserData(
                onNameCreate = { name = it },
                onSurnameCreate = { surname = it },
                onPatronymicCreate = { patronymic = it },
                onEmailCreate = { email = it }
            )
        }


        Column(
            modifier = Modifier.weight(1f, false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Button(
                onClick = {
                    viewModel.onTriggerEvent(
                        eventType = RegistrationUserDataEvent.CreateUserData(
                            name,
                            surname,
                            patronymic,
                            email
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
                    text = stringResource(id = R.string.further),
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserData(
    onNameCreate: (name: String) -> Unit,
    onSurnameCreate: (surname: String) -> Unit,
    onPatronymicCreate: (patronymic: String) -> Unit,
    onEmailCreate: (email: String) -> Unit
) {

    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val patronymic = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current


    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        OutlinedTextField(
            value = surname.value,
            onValueChange = {
                surname.value = it
                onSurnameCreate(surname.value)
            },
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.surname),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Grey900,
                backgroundColor = Color.White,
                focusedIndicatorColor = Grey300,
                unfocusedIndicatorColor = Grey300,
                cursorColor = Color.Black,
                placeholderColor = GreyBlue
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
                onNameCreate(name.value)
            },
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.name),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Grey900,
                backgroundColor = Color.White,
                focusedIndicatorColor = Grey300,
                unfocusedIndicatorColor = Grey300,
                cursorColor = Color.Black,
                placeholderColor = GreyBlue,
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        )

        OutlinedTextField(
            value = patronymic.value,
            onValueChange = {
                patronymic.value = it
                onPatronymicCreate(patronymic.value)
            },
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.patronymic),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Grey900,
                backgroundColor = Color.White,
                focusedIndicatorColor = Grey300,
                unfocusedIndicatorColor = Grey300,
                cursorColor = Color.Black,
                placeholderColor = GreyBlue
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                onEmailCreate(email.value)
            },
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.email),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Grey900,
                backgroundColor = Color.White,
                focusedIndicatorColor = Grey300,
                unfocusedIndicatorColor = Grey300,
                cursorColor = Color.Black,
                placeholderColor = GreyBlue
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}