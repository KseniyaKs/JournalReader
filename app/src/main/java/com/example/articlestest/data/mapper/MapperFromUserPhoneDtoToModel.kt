package com.example.articlestest.data.mapper

import com.example.articlestest.data.dto.UserPhoneDto
import com.example.articlestest.data.model.UserPhone
import javax.inject.Inject

interface MapperFromUserPhoneDtoToModel {
    fun map(phoneDto: UserPhoneDto): UserPhone
}

class MapperFromUserPhoneDtoToModelImpl @Inject constructor() : MapperFromUserPhoneDtoToModel {
    override fun map(phoneDto: UserPhoneDto): UserPhone {
        return UserPhone(phone = phoneDto.phone)
    }

}