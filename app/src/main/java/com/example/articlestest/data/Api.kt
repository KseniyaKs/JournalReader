package com.example.articlestest.data

import com.example.articlestest.data.dto.AuthorizationCheckDto
import com.example.articlestest.data.dto.TokenDto
import com.example.articlestest.presentation.screens.authorization.password.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("confirm/create/")
    suspend fun authorizationCheck(@Body phone: Phone): Response<AuthorizationCheckDto>

    @POST("sign-in/")
    suspend fun signIn(@Body user: User): Response<TokenDto>

}
