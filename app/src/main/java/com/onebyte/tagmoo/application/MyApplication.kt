package com.onebyte.tagmoo.application

import com.onebyte.tagmoo.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
            .apply{
                inject(this@MyApplication)
            }
    }

}