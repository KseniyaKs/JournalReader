package com.example.articlestest.presentation.authorization.new_password

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.articlestest.presentation.MainActivity
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.theme.Grey300
import com.example.articlestest.presentation.theme.Grey900
import com.example.articlestest.presentation.theme.Pink
import com.example.articlestest.presentation.view.Back
import com.example.articlestest.presentation.view.ButtonMaxWidthWithText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthorizationNewPasswordFragment : Fragment() {

    companion object {
        private const val PHONE = "PHONE"

        fun newInstance(phone: String) = AuthorizationNewPasswordFragment().apply {
            arguments = Bundle().apply {
                putSerializable(PHONE, phone)
            }
        }
    }

    val viewModel: AuthorizationNewPasswordViewModel by viewModels()

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
                    AuthorizationNewPasswordScreen(
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
fun AuthorizationNewPasswordScreen(
    viewModel: AuthorizationNewPasswordViewModel,
    phone: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val password = remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Log.d("asdasd", "password_screen")

            Back { viewModel.onBack() }

            Text(
                text = stringResource(id = R.string.enter_password),
                fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
            )

            Text(
                text = stringResource(id = R.string.password),
                fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
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
                value = password.value,
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                    fontSize = 20.sp,
                ),
                onValueChange = { password.value = it }
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
                enabled = password.value.isNotEmpty(),
                onClick = {
                    viewModel.onTriggerEvent(
                        eventType = AuthorizationNewPasswordEvent.CreateNewPassword(
                            phone = phone,
                            password = password.value,
                            newPassword = password.value
                        )
                    )
                }
            )
        }
    }
}
