package com.example.articlestest.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.articlestest.R
import com.example.articlestest.ui.theme.BrightRed
import com.example.articlestest.ui.theme.Grey300
import com.example.articlestest.ui.theme.Grey900

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OTPTextFields(
    modifier: Modifier = Modifier,
    length: Int,
    onFilled: (code: String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var codeList: List<String> by remember { mutableStateOf(listOf()) }
    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }

    Row(
        modifier = Modifier.padding(PaddingValues(bottom = 12.dp)),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (0 until length).forEach { index ->
            OutlinedTextField(
                modifier = Modifier
                    .width(56.dp)
                    .height(56.dp)
                    .background(Color.White)
                    .focusRequester(focusRequester = focusRequesters[index])
                    .focusProperties(scope = {}),
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily(Font(R.font.gilroy_regular)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center, color = Color.Black
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Grey900,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Grey300,
                    unfocusedIndicatorColor = Grey300,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                value = codeList.getOrNull(index = index) ?: "",
                onValueChange = { value: String ->
                    if (focusRequesters[index].freeFocus()) {
                        val temp = codeList.toMutableList()
                        if (value == "") {
                            if (temp.size > index) {
                                temp.removeAt(index = index)
                                codeList = temp
                                focusRequesters.getOrNull(index - 1)?.requestFocus()
                            }
                        } else {
                            if (codeList.size > index) {
                                temp[index] = value//.getOrNull(0) //?: ""
                            } else {
                                temp.add(value)//.getOrNull(0) ?: ' ')
                                codeList = temp
                                focusRequesters.getOrNull(index + 1)?.requestFocus() ?: onFilled(
                                    codeList.joinToString(separator = "")
                                )
                            }
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    //send confirmation code to server
                    keyboardController?.hide()
                    /*if () {} else */
                }),
                visualTransformation = VisualTransformation.None//PasswordVisualTransformation()
            )
        }
    }
}

@Composable
fun IncorrectCode() {
    Text(
        text = stringResource(id = R.string.incorrect_code),
        fontSize = 12.sp,
        color = BrightRed,
        fontFamily = FontFamily(Font(R.font.gilroy_medium)),
    )
}
