package com.onebyte.tagmoo.di.module

import com.onebyte.tagmoo.services.networkServices.NetworkService
import com.onebyte.tagmoo.services.repositories.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideRepository(networkService : NetworkService): Repository {
        return Repository(networkService)
    }
}