package com.example.articlestest.domain

interface RegistrationRepository {
    suspend fun checkConfirmCode(phone: String, code: String): Boolean

    suspend fun signUp(phone: String, password: String): Boolean
}