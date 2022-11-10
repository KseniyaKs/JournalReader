package com.example.articlestest.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.articlestest.data.mapper.MapperFromAuthorizationCheckDtoToModel
import com.example.articlestest.data.mapper.MapperFromUserPhoneDtoToModel
import com.example.articlestest.data.model.AuthorizationCheck
import com.example.articlestest.data.model.UserPhone
import com.example.articlestest.domain.PreferenceKeys
import com.example.articlestest.domain.repositories.AuthorisationRepository
import com.example.articlestest.presentation.base.ResponseMapper
import javax.inject.Inject


class AuthorisationRepositoryImpl @Inject constructor(
    private val api: Api,
    private val mapper: ResponseMapper,
    private val mapperFromAuthorizationCheckDtoToModel: MapperFromAuthorizationCheckDtoToModel,
    private val mapperFromUserPhoneDtoToModel: MapperFromUserPhoneDtoToModel,
    private val sharedPreferences: DataStore<Preferences>
) : AuthorisationRepository {

    override suspend fun checkPhone(phone: String): AuthorizationCheck {
        val response = mapper.map(api.checkPhone(PhoneBody(phone = phone)))
        return mapperFromAuthorizationCheckDtoToModel.map(response)
    }

    override suspend fun signIn(phone: String, password: String): Boolean {
        val request = api.signIn(UserBody(username = phone, password = password))
        val isSuccessful = if (request.isSuccessful) {
            val response = mapper.map(request)

            sharedPreferences.edit { preferences ->
                preferences[PreferenceKeys.USER_PHONE] = phone
                preferences[PreferenceKeys.PASSWORD] = password
                preferences[PreferenceKeys.ACCESS_TOKEN] = response.accessToken
                preferences[PreferenceKeys.REFRESH_TOKEN] = response.refreshToken
            }
            true
        } else false

        return isSuccessful
    }

    override suspend fun createConfirmCode(phone: String): UserPhone {
        val response = mapper.map(api.createConfirmCode(PhoneBody(phone = phone)))
        return mapperFromUserPhoneDtoToModel.map(response)
    }

    override suspend fun checkConfirmCode(phone: String, code: String): Boolean {
        val request = api.checkConfirmCode(phone, code)
        val isSuccessful = if (request.isSuccessful) {
            mapper.map(request)
            true
        } else false
        return isSuccessful
    }

    override suspend fun createNewPassword(phone: String, password: String, newPassword: String) {
        api.createNewPassword(
            NewPasswordBody(
                username = phone,
                password1 = password,
                password2 = newPassword
            )
        )
        sharedPreferences.edit { preferences ->
            preferences[PreferenceKeys.PASSWORD] = newPassword
        }
    }

    override suspend fun createNewToken(token: String) {
        val response = mapper.map(api.createNewToken(TokenBody(token)))

        sharedPreferences.edit { preferences ->
            preferences[PreferenceKeys.ACCESS_TOKEN] = response.accessToken
        }
    }

    override suspend fun isNotEmptyProfile(): Boolean {
        val response = mapper.map(api.getUserInfo())

        return !(response.name.isNullOrEmpty() ||
                response.surname.isNullOrEmpty() ||
                response.email.isNullOrEmpty() ||
                response.city == null)
    }
}