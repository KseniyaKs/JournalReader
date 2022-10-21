package com.example.articlestest.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.articlestest.data.Api
import com.example.articlestest.data.AuthorisationRepositoryImpl
import com.example.articlestest.data.RegistrationRepositoryImpl
import com.example.articlestest.data.mapper.MapperFromAuthorizationCheckDtoToModel
import com.example.articlestest.data.mapper.MapperFromAuthorizationCheckDtoToModelImpl
import com.example.articlestest.data.mapper.MapperFromUserPhoneDtoToModel
import com.example.articlestest.data.mapper.MapperFromUserPhoneDtoToModelImpl
import com.example.articlestest.domain.AuthorisationRepository
import com.example.articlestest.domain.RegistrationRepository
import com.example.articlestest.huinya.base.ResponseMapper
import com.example.articlestest.huinya.base.ResponseMapperImpl
import com.example.articlestest.huinya.base.test.ArticleRepository
import com.example.articlestest.huinya.base.test.ArticleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    @Singleton
    fun provideArticleRepository(api: Api): ArticleRepository {
        return ArticleRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthorizationRepository(
        api: Api,
        mapper: ResponseMapper,
        mapperFromAuthorizationCheckDtoToModel: MapperFromAuthorizationCheckDtoToModel,
        mapperFromUserPhoneDtoToModel: MapperFromUserPhoneDtoToModel,
        sharedPreferences: DataStore<Preferences>
    ): AuthorisationRepository {
        return AuthorisationRepositoryImpl(
            api,
            mapper,
            mapperFromAuthorizationCheckDtoToModel,
            mapperFromUserPhoneDtoToModel,
            sharedPreferences
        )
    }

    @Provides
    @Singleton
    fun provideRegistrationRepository(
        api: Api,
        mapper: ResponseMapper,
        sharedPreferences: DataStore<Preferences>
    ): RegistrationRepository {
        return RegistrationRepositoryImpl(
            api,
            mapper,
            sharedPreferences
        )
    }


    @Provides
    @Singleton
    fun provideResponseMapper(): ResponseMapper {
        return ResponseMapperImpl()
    }

    @Provides
    @Singleton
    fun provideMapperFromAuthorizationCheckDtoToModel(): MapperFromAuthorizationCheckDtoToModel {
        return MapperFromAuthorizationCheckDtoToModelImpl()
    }


    @Provides
    @Singleton
    fun provideMapperFromUserPhoneDtoToModel(): MapperFromUserPhoneDtoToModel {
        return MapperFromUserPhoneDtoToModelImpl()
    }


}

