package com.example.articlestest.domain

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val USER_PHONE = stringPreferencesKey("user_phone")
    val PASSWORD = stringPreferencesKey("password")

    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")

}