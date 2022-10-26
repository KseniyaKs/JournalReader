package com.example.articlestest.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class AuthorizationInterceptor @Inject constructor(
    sharedPreferences: DataStore<Preferences>
) : Interceptor {

    private var token: String? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            sharedPreferences.data.collect {
                token = it[PreferenceKeys.ACCESS_TOKEN]
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        if (token != null) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token").build()
        }
        return chain.proceed(request)
    }
}