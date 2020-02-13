package com.onebyte.tagmoo.di.module

import android.content.Context
import com.onebyte.tagmoo.application.MyApplication
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideApplication(application: MyApplication): Context
}