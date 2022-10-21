package com.example.articlestest.data.dto

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("refresh") val refreshToken: String,
    @SerializedName("access") val accessToken: String
)
