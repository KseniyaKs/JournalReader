package com.example.articlestest.domain.repositories

import com.example.articlestest.data.model.AuthorizationCheck
import com.example.articlestest.data.model.UserPhone


interface AuthorisationRepository {
    suspend fun checkPhone(phone: String): AuthorizationCheck

    suspend fun signIn(phone: String, password: String): Boolean

    suspend fun createConfirmCode(phone: String): UserPhone

    suspend fun checkConfirmCode(phone: String, code: String): Boolean

    suspend fun createNewPassword(phone: String, password: String, newPassword: String)
}