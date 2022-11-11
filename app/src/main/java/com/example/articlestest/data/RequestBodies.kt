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

data class UserInfoBody(
    val first_name: String,
    val last_name: String,
    val patronymic: String,
    val email: String,
    val city_pk: String,
)

data class TokenBody(
    val refresh: String
)

data class CommentBody(
    val text: String
)