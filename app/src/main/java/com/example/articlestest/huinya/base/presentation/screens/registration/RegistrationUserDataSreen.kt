package com.example.articlestest.huinya.base.presentation.screens.registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.articlestest.R
import com.example.articlestest.ui.theme.Grey300
import com.example.articlestest.ui.theme.Grey900
import com.example.articlestest.ui.theme.GreyBlue
import com.example.articlestest.ui.theme.Pink

@Composable
fun RegistrationUserDataScreen(
    onAuthPasswordContinueClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            LogoAndBack(navController)

            Text(
                text = stringResource(id = R.string.registration_help),
                fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(PaddingValues(bottom = 43.dp))
            )

            UserData()
        }


        Column(
            modifier = Modifier.weight(1f, false),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Button(
                onClick = onAuthPasswordContinueClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(37.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Pink)
            ) {
                Text(
                    text = stringResource(id = R.string.further),
                    fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
                    fontSize = 17.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun UserData() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
            value = "Фамилия",
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                fontSize = 20.sp,
                color = GreyBlue,
                textAlign = TextAlign.Center
            ),
            onValueChange = {}
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
            value = "Имя",
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                fontSize = 20.sp,
                color = GreyBlue,
                textAlign = TextAlign.Center
            ),
            onValueChange = {}
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
            value = "Отчество",
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                fontSize = 20.sp,
                color = GreyBlue,
                textAlign = TextAlign.Center
            ),
            onValueChange = {}
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
            value = "Электронная почта",
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                fontSize = 20.sp,
                color = GreyBlue,
                textAlign = TextAlign.Center
            ),
            onValueChange = {}
        )
    }
}