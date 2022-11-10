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

    @POST("token/refresh/")
    suspend fun createNewToken(@Body token: TokenBody): Response<TokenDto>

    @GET("user_info/")
    suspend fun getUserInfo(): Response<UserInfoDto>


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

    @GET("journal/{id}/")
    suspend fun getJournalDetails(@Path("id") id: String): Response<JournalDto>

    @GET("page/{id}/")
    suspend fun getJournalPage(@Path("id") id: String): Response<JournalPageDto>

//    @POST("page/{id}/add_comment/")
//    suspend fun addJournalsComment(@Path("id") id: String): Response<>

//    @POST("page/{id}/add_like/")
//    suspend fun addJournalsLike(@Path("id") id: String): Response<>

    //articles
    @GET("article/")
    suspend fun getArticles(): Response<ArticlesDto>

    @GET("article/{id}/")
    suspend fun getArticleDetails(@Path("id") id: String): Response<ArticleDto>

//    @POST("page/{id}/add_comment/")
//    suspend fun addArticlesComment(@Path("id") id: String): Response<>

//    @POST("page/{id}/add_like/")
//    suspend fun addArticlesLike(@Path("id") id: String): Response<>
}
