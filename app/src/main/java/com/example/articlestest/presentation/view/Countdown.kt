package com.example.articlestest.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Countdown(
    time: Int, max: Int, onAction: (Action) -> Unit
) {
    Box() {
        ClickableText(
            text = AnnotatedString(text = "Отправить код повторно (доступно через $time сек.)"),
            style = TextStyle(
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.padding(8.dp),
            onClick = {}
        )
    }
}

sealed class Action {
    object Play : Action()
    object Stop : Action()
}

//            ClickableText(
//                text = AnnotatedString(
//                    text = text,
//                    spanStyles = listOf(
//                        AnnotatedString.Range(
//                            SpanStyle(
//                                fontSize = 12.sp,
//                                fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
//                                color = HyperLinkBlue
//                            ),
//                            start = 0,
//                            end = text.length
//                        )
//                    )
//                ),
//                modifier = Modifier.padding(PaddingValues(bottom = 42.dp)),
//                onClick = {})