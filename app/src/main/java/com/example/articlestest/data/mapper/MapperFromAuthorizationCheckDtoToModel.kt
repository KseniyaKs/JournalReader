package com.example.articlestest.data.mapper

import com.example.articlestest.data.dto.AuthorizationCheckDto
import com.example.articlestest.data.model.AuthorizationCheck

interface MapperFromAuthorizationCheckDtoToModel {
    fun map(dto: AuthorizationCheckDto): AuthorizationCheck
}

class MapperFromAuthorizationCheckDtoToModelImpl : MapperFromAuthorizationCheckDtoToModel {
    override fun map(dto: AuthorizationCheckDto): AuthorizationCheck {
        return AuthorizationCheck(phone = dto.phone, is_authorized = dto.is_authorized)
    }

}