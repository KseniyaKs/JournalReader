package com.example.articlestest.data.dto

import com.google.gson.annotations.SerializedName

data class NewPasswordDto(
    @SerializedName("message") val message: String
)
