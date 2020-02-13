package com.onebyte.tagmoo.di.component

import android.app.Application
import com.onebyte.tagmoo.application.MyApplication
import com.onebyte.tagmoo.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton
import dagger.android.AndroidInjectionModule

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        ActivitiesModule::class,
        NetworkServiceModule::class,
        NetworkRequestModule::class,
        RepositoriesModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication> {

    override fun inject(instance: MyApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}