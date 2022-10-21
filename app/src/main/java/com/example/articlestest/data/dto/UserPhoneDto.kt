package com.example.articlestest.data.dto

import com.google.gson.annotations.SerializedName

data class UserPhoneDto(
    @SerializedName("phone") val phone: String
)
