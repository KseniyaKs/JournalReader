package com.example.articlestest.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
}