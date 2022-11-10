package com.example.articlestest.data.dto

import com.google.gson.annotations.SerializedName

data class UserInfoDto(
    @SerializedName("first_name") val name: String?,
    @SerializedName("last_name") val surname: String?,
    @SerializedName("patronymic") val patronymic: String,
    @SerializedName("email") val email: String?,
    @SerializedName("city") val city: CityInfoDto?,
)

data class CityInfoDto(
    @SerializedName("id") val id: Number,
    @SerializedName("name") val name: String
)
