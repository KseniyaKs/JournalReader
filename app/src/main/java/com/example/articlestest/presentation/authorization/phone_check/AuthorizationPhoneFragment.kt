package com.example.articlestest.presentation.authorization.phone_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
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
import com.example.articlestest.presentation.authorization.password_check.AuthorizationPasswordFragment
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.registration.confirmcode_check.RegistrationConfirmCodeFragment
import com.example.articlestest.presentation.view.HyperlinkText
import com.example.articlestest.ui.theme.Grey300
import com.example.articlestest.ui.theme.Grey900
import com.example.articlestest.ui.theme.Pink
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthorizationPhoneFragment : Fragment() {

    companion object {
        fun newInstance() = AuthorizationPhoneFragment()
    }

    private val viewModel: AuthorizationCheckViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_authorization_phone, container, false)
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    AuthorizationPhoneScreen(viewModel)//, phone)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.AuthorizationPassword -> {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.container,
                            AuthorizationPasswordFragment.newInstance(destination.phone)
                        )
                        .addToBackStack("authorization_password")
                        .commit()
                }
                is NavDestination.RegistrationConfirmationCode -> {
                    parentFragmentManager
                        .beginTransaction()
                        .add(
                            R.id.container,
                            RegistrationConfirmCodeFragment.newInstance(destination.phone)
                        )
                        .addToBackStack("registration_confirm_code")
                        .commit()
                }
                else -> {}
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthorizationPhoneScreen(
    viewModel: AuthorizationCheckViewModel,
) {

    val phoneNumber = remember { mutableStateOf("") } //89279442890
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 35.dp,
                    bottom = 24.dp
                )
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_small),
                contentDescription = null,
                modifier = Modifier.padding(PaddingValues(bottom = 45.dp))
            )

            Text(
                text = stringResource(id = R.string.telephone_number),
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                modifier = Modifier.padding(PaddingValues(bottom = 8.dp))
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = true,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Grey900,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Grey300,
                    unfocusedIndicatorColor = Grey300,
                    cursorColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                    fontSize = 20.sp,
                ),
                value = phoneNumber.value,
                onValueChange = {
                    phoneNumber.value = it
                }
            )
        }


        Column(
            modifier = Modifier.weight(1f, false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            HyperlinkText(
                fullText = "Нажимая кнопку «Продолжить» вы соглашаетесь с пользовательским соглашением и политикой конфиденцальности",
                linkText = listOf("пользовательским соглашением", "политикой конфиденцальности"),
                hyperlinks = listOf(
                    "http://breaking-bad-online.ru/sezon-2/27-2-sezon-10-seriya-konec-biznesa.html",
                    "https://ffont.ru/font/gilroy"
                ),
                boldText = "«Продолжить»",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                modifier = Modifier.padding(PaddingValues(bottom = 24.dp)),
                textAlign = TextAlign.Center
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(37.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Pink),
                onClick = {
                    viewModel.onTriggerEvent(
                        eventType = AuthorizationCheckContractEvent.PhoneCheck(
                            phone = phoneNumber.value
                        )
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