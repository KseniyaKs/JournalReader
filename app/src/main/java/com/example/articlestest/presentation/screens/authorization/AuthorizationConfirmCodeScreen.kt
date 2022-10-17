package com.example.articlestest.presentation.screens.authorization

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.articlestest.R
import com.example.articlestest.presentation.view.Countdown
import com.example.articlestest.presentation.view.OTPTextFields
import com.example.articlestest.ui.theme.Pink
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun AuthorizationConfirmCodeScreen(navController: NavController) {

    val otpVal: String? = null

    val step = 1000
    val max = 70
    val timeLiveData = MutableStateFlow(max)

    val timer = object : CountDownTimer(max * 1000L, step.toLong()) {
        override fun onTick(millisUntilFinished: Long) {
            if (timeLiveData.value > 0) {
//                 val current = timeLiveData.value ?: 0
                timeLiveData.value -= 1
                Log.d("asd", timeLiveData.value.toString())

            } else {
                onFinish()
            }
        }

        override fun onFinish() {
            timeLiveData.tryEmit(max)
        }
    }.start()

//    Log.d("asd1", timeLiveData.value.toString())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
//            LogoAndBack(navController)

            Text(
                text = stringResource(id = R.string.send_code),
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
            )

            Text(
                text = stringResource(id = R.string.confirmation_code),
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                modifier = Modifier.padding(PaddingValues(top = 35.dp, bottom = 8.dp))
            )

            OTPTextFields(length = 4) { getOpt -> otpVal }

            //IncorrectCode()
        }

        Column(
            modifier = Modifier.weight(1f, false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val sec = 0
            val text = "Отправить код повторно (доступно через $sec сек.)"


            val timeValue by timeLiveData.collectAsState(max)
//            Log.d("asd", timeValue.toString())

            Countdown(time = timeValue, max = max) {
//                timer.start()
//                when (it) {
//                    Action.Play -> {
//                        timer.start()
//                    }
//                    Action.Stop -> {
//                        timer.cancel()
//                        timer.onFinish()
//                    }
//                }
            }

            Button(
                onClick = { /*TODO*/ },
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
