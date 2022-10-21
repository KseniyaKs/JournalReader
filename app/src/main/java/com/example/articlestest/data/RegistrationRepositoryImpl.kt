package com.example.articlestest.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.articlestest.domain.PreferenceKeys
import com.example.articlestest.domain.RegistrationRepository
import com.example.articlestest.huinya.base.ResponseMapper
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: Api,
    private val mapper: ResponseMapper,
    private val sharedPreferences: DataStore<Preferences>
) : RegistrationRepository {
    override suspend fun checkConfirmCode(phone: String, code: String): Boolean {
        val request = api.checkRegistrationConfirmCode(phone, code)
        val isSuccessful = if (request.isSuccessful) {
            mapper.map(request)
            true
        } else false
        return isSuccessful

    }

    override suspend fun signUp(phone: String, password: String): Boolean {
        val request = api.signUp(UserBody(username = phone, password = password))
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
}