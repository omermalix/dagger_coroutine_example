package com.onebyte.tagmoo.di.module

import com.onebyte.tagmoo.services.networkServices.NetworkService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object NetworkServiceModule {
    @Provides
    @Singleton
    fun provideRemoteServiceDao(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

}