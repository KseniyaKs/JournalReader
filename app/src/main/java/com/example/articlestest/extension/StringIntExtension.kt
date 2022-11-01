package com.example.articlestest.extension


fun String.floatToInt(): Int {
    return this.toFloat().toInt()
}

fun Int.getRubleAddition(): String {
    val preLastDigit = this % 100 / 10
    return if (preLastDigit == 1) {
        "$this рублей"
    } else when (this % 10) {
        1 -> "$this рубль"
        2, 3, 4 -> "$this рубля"
        else -> "$this рублей"
    }
}

fun String.monthNumber(): String {
    return when (this.lowercase()) {
        "январь" -> {
            "01"
        }
        "февраль" -> {
            "02"
        }
        "март" -> {
            "03"
        }
        "апрель" -> {
            "04"
        }
        "май" -> {
            "05"
        }
        "июнь" -> {
            "06"
        }
        "июль" -> {
            "07"
        }
        "август" -> {
            "08"
        }
        "сентябрь" -> {
            "09"
        }
        "октябрь" -> {
            "10"
        }
        "ноябрь" -> {
            "11"
        }
        "декабрь" -> {
            "12"
        }
        else -> {
            ""
        }
    }
}

