package com.example.articlestest.presentation.authorization.phone_check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
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
import com.example.articlestest.presentation.components.HyperlinkText
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.registration.confirmcode_check.RegistrationConfirmCodeFragment
import com.example.articlestest.presentation.theme.Grey300
import com.example.articlestest.presentation.theme.Grey900
import com.example.articlestest.presentation.theme.Pink
import com.example.articlestest.presentation.view.ButtonMaxWidthWithText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthorizationPhoneFragment : Fragment() {

    companion object {
        fun newInstance() = AuthorizationPhoneFragment()
    }

    private val viewModel: AuthorizationPhoneViewModel by viewModels()

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
                    AuthorizationPhoneScreen(viewModel)
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
    viewModel: AuthorizationPhoneViewModel,
) {

    val phoneNumber = remember { mutableStateOf("") } //89279442890
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
                modifier = Modifier.padding(PaddingValues(bottom = 8.dp))
            )

            OutlinedTextField(
                value = phoneNumber.value,
                onValueChange = {
                    phoneNumber.value = it
                },
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                    fontSize = 20.sp,
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Grey900,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Grey300,
                    unfocusedIndicatorColor = Grey300,
                    cursorColor = Color.Black
                ),
                enabled = true,
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

//            AndroidView(
//                factory = { context ->
//                    val view = LayoutInflater.from(context).inflate(R.layout.edit_text_layout, null, false)
//                    val textView = view.findViewById<EditText>(R.id.edit_text)


//                    MaskedTextChangedListener.installOn(
//                        phone.findViewById(R.id.edit_text),
//                        "+7 ([000]) [000]-[00]-[00]",
//                        object : MaskedTextChangedListener.ValueListener {
//                            override fun onTextChanged(
//                                maskFilled: Boolean,
//                                extractedValue: String,
//                                formattedValue: String
//                            ) {
//                                viewModel.onPhoneNumberChanged(formattedValue)
//                            }
//                        }
//                    )

            // do whatever you want...
//                    view // return the view
//                },
//                update = { view ->
//                    // Update the view
//                }
//            )
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
                fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                modifier = Modifier.padding(PaddingValues(bottom = 24.dp)),
                textAlign = TextAlign.Center
            )

            ButtonMaxWidthWithText(
                background = Pink,
                text = stringResource(id = R.string.continue_button),
                textColor = Color.White,
                enabled = phoneNumber.value.isNotEmpty(),
                onClick = {
                    viewModel.onTriggerEvent(
                        eventType = AuthorizationCheckContractEvent.PhoneCheck(
                            phone = phoneNumber.value
                        )
                    )
                }
            )
        }
    }
}