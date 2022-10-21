package com.example.articlestest.presentation.registration.create_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.articlestest.R
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.registration.user_data.RegistrationUserDataFragment
import com.example.articlestest.ui.theme.AlphaBlack
import com.example.articlestest.ui.theme.Grey300
import com.example.articlestest.ui.theme.Grey900
import com.example.articlestest.ui.theme.Pink
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrationPasswordFragment()
    }

    val viewModel: RegistrationPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.BackClick -> {
                    parentFragmentManager.popBackStack()
                }
                is NavDestination.RegistrationUserData -> {
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

@Composable
fun RegistrationPasswordScreen(
    onAuthPasswordContinueClick: () -> Unit
) {

    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_small),
                contentDescription = null,
                modifier = Modifier.padding(PaddingValues(bottom = 45.dp))
            )

            Text(
                text = stringResource(id = R.string.password),
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                modifier = Modifier.padding(PaddingValues(bottom = 8.dp))
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
                value = password.value,
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                    fontSize = 20.sp,
                ),
                onValueChange = { password.value = it }
            )

            Text(
                text = stringResource(id = R.string.password_help),
                fontSize = 12.sp,
                color = AlphaBlack,
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                modifier = Modifier.padding(PaddingValues(top = 8.dp))
            )
        }


        Column(
            modifier = Modifier.weight(1f, false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Button(
                onClick = {},
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