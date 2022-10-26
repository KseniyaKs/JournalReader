package com.example.articlestest.domain.repositories

import com.example.articlestest.data.model.City

interface RegistrationRepository {
    suspend fun checkConfirmCode(phone: String, code: String): Boolean

    suspend fun signUp(phone: String, password: String): Boolean

    suspend fun getCities(): List<City>

    suspend fun createUserInfo(
        name: String,
        surname: String,
        patronymic: String,
        email: String,
        city: String
    )

}