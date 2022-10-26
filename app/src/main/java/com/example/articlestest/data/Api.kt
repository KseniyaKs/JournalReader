package com.example.articlestest.data

import com.example.articlestest.data.dto.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    //authorization
    @POST("confirm/create/")
    suspend fun checkPhone(@Body phoneBody: PhoneBody): Response<AuthorizationCheckDto>

    @POST("sign-in/")
    suspend fun signIn(@Body user: UserBody): Response<TokenDto>

    @POST("confirm_pass/create/")
    suspend fun createConfirmCode(@Body phoneBody: PhoneBody): Response<UserPhoneDto>

    @FormUrlEncoded
    @POST("confirm_pass/confirmed/")
    suspend fun checkConfirmCode(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Response<CheckConfirmCodeDto>

    @POST("reset_password/")
    suspend fun createNewPassword(@Body newPasswordBody: NewPasswordBody): Response<NewPasswordDto>


    //registration
    @FormUrlEncoded
    @POST("confirm/confirmed/")
    suspend fun checkRegistrationConfirmCode(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Response<CheckConfirmCodeDto>

    @POST("sign-up/")
    suspend fun signUp(@Body user: UserBody): Response<TokenDto>

    @GET("cities/")
    suspend fun getCities(): Response<List<CityInfoDto>>

    @PATCH("user_info/update/")
    suspend fun createUserInfo(@Body userIfo: UserInfoBody): Response<UserInfoDto>

    //journal
    @GET("journal/")
    suspend fun getJournals(): Response<JournalsDto>


//
//    @POST("token/refresh/")
//    suspend fun createNewPToken(@Body user: UserBody): Response<>


}
