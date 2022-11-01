package com.example.articlestest.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.articlestest.presentation.theme.HyperLinkBlue
import kotlinx.coroutines.delay
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun Countdown(
    modifier: Modifier,
    max: Int = 60,
    onResendConfirmCode: () -> Unit
) {

    var ticks by remember { mutableStateOf(max) }
    var isCanResend by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        while (0 < ticks) {
            delay(1.toDuration(DurationUnit.SECONDS))
            ticks--
            isCanResend = false
        }
        if (ticks == 0) {
            isCanResend = true
        }
    }

    val timerString = buildAnnotatedString {
        append("Отправить код повторно (доступно через ")
        append(ticks.toString())
        append(" сек.)")
    }

    Box(modifier = modifier) {
        Text(
            text = timerString,
            color = HyperLinkBlue,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier
                .padding(8.dp)
                .clickable(
                    onClick = {
                        if (isCanResend) onResendConfirmCode()
                    }
                )
        )
    }
}

