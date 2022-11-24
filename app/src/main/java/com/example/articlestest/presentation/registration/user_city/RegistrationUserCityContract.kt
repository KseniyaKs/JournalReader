package com.example.articlestest.presentation.registration.user_city

sealed class RegistrationUserCityViewState {
    //    data class Cities(val cities: List<City>) : RegistrationUserCityViewState()
    object CreateUser : RegistrationUserCityViewState()
}


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