package com.example.articlestest.presentation.registration.user_city

import com.example.articlestest.data.model.City

data class RegistrationUserCityViewState(
    val cities: List<City>
)

sealed class RegistrationUserCityEvent {
    class CreateUserInfo(
        val name: String,
        val surname: String,
        val patronymic: String,
        val email: String,
        val city: String
    ) : RegistrationUserCityEvent()

    object GetCity : RegistrationUserCityEvent()
}