package com.example.articlestest.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.articlestest.R
import com.example.articlestest.presentation.theme.DarkGrey
import com.example.articlestest.presentation.theme.Pink


@Composable
fun JournalPrice(price: String, modifier: Modifier) {

    Column(modifier = Modifier.padding(top = 29.dp)) {
        Text(
            text = "$price рублей",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
            color = Pink
        )

        Text(
            text = "в месяц",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_light_300)),
            color = DarkGrey
        )
    }
}

@Composable
fun IssueNumber(month: String, issue: String, modifier: Modifier) {

    Column(modifier = modifier) {
        Text(
            text = "№ $issue",
            fontFamily = FontFamily(Font(R.font.poiret_one_regular_400)),
            fontSize = 20.sp
        )

        Text(
            text = month,
            fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
            fontSize = 17.sp
        )
    }
}