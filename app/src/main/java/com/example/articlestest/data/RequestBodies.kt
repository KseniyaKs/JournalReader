package com.example.articlestest.data

data class PhoneBody(val phone: String)

data class UserBody(
    val username: String,
    val password: String
)

data class NewPasswordBody(
    val username: String,
    val password1: String,
    val password2: String
)
