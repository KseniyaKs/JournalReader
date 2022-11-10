package com.example.articlestest.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun Test() {

    var mobileNumber = remember { mutableStateOf("") }
    Column {
        Row(modifier = Modifier.padding(all = 10.dp)) {
            Text(
                text = "Mobile number",
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )
            BasicTextField(
                value = mobileNumber.value,
                onValueChange = { mobileNumber.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = { maskFilter(it) }
            )
        }
        Box(
            modifier = Modifier
                .height(1.dp)
                .padding(start = 10.dp)
                .fillMaxWidth()
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Actual value:\n$mobileNumber")
    }

}


//const val mask = "+7 (xxx) xxx xx xx"
//const val mask = "+5 xx xx"

//
//fun mobileNumberFilter(text: AnnotatedString): TransformedText {
//    // change the length
//    val trimmed =
//        if (text.text.length >= 12) text.text.substring(0..11) else text.text
//
//    val annotatedString = AnnotatedString.Builder().run {
//        for (i in trimmed.indices) {
//            if (trimmed[0].code != 7) {
//                append("+7")
//            }
//            append(trimmed[i])
//
//            when (i) {
//                1 -> append(" (")
//                4 -> append(") ")
//                7 -> append(" ")
//                9 -> append(" ")
//
//            }
//
//        }
//
//        pushStyle(SpanStyle(color = Color.LightGray))
//        append(mask.takeLast(mask.length - length))
//        toAnnotatedString()
//
//    }
//
//    val phoneNumberFormattingTextWatcher = object : PhoneNumberFormattingTextWatcher() {
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            super.onTextChanged(s, start, before, count)
//
//        }
////    }
//    val phoneNumberOffsetTranslator = object : OffsetMapping {
//        override fun originalToTransformed(offset: Int): Int {
//            if (offset <= 1) return offset
//            if (offset <= 4) return offset + 1
//            if (offset <= 7) return offset + 2
//            if (offset <= 9) return offset + 3
//            return 12
//        }
//
//        override fun transformedToOriginal(offset: Int): Int {
//            if (offset <= 1) return offset
//            if (offset <= 4) return offset - 1
//            if (offset <= 10) return offset - 2
//            if (offset <= 12) return offset - 3
//            return 9
//        }
//    }
//
//    return TransformedText(annotatedString, phoneNumberOffsetTranslator)
//}


//+7 (999) 111 22 33
//1) номер начался не с 7 и не с 8 -> автомтаический вставить +7 и введенную цифру после него
//2) Номер начаося с 7 -> ничего не делаем
//3) Номер начался с 8 -> Заменяем на +7


//    val asd = object : PhoneNumberFormattingTextWatcher("+7") {
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            super.onTextChanged(s, start, before, count)
//        }
//    }
////    val affineFormats = mutableListOf<String>()
////    affineFormats.add("+7 ([000]) [000]-[00]-[00]")
//
//
////    val symbols = listOf<String>( "1", "2", "3", "4", "5", "6", "9", "0")
//
//////    "+"
//////    "7", "8",
////
////    when(number) {
////
////
////    }

class MaskTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return maskFilter(text)
    }
}


fun maskFilter(text: AnnotatedString): TransformedText {

    // NNNNN-NNN
    //+7 (xxx) xxx xx xx
    val trimmed = if (text.text.length >= 12) text.text.substring(0..11) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        when (i) {
            1 -> out += " ("
            4 -> out += ") "
            7 -> out += " "
            9 -> out += " "
        }
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 4) return offset + 1
            if (offset <= 7) return offset + 2
            if (offset <= 9) return offset + 3

            return 11

        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 8) return offset - 2
            if (offset <= 10) return offset - 3
            if (offset <= 10) return offset - 4

            return 12
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}