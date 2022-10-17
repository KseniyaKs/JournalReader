package com.example.articlestest.data.dto

import com.google.gson.annotations.SerializedName


data class AuthorizationCheckDto(
    @SerializedName("phone") val phone: String,
    @SerializedName("is_authorized") val is_authorized: Boolean
)
