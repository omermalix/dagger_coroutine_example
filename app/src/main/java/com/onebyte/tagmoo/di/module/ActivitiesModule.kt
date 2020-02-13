package com.onebyte.tagmoo.di.module

import com.onebyte.tagmoo.ui.cameraView.CameraActivity
import com.onebyte.tagmoo.ui.takePictureView.TakePictureActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    internal abstract fun contributeCameraActivity(): CameraActivity
    @ContributesAndroidInjector
    internal abstract fun contributeTakePictureActivity(): TakePictureActivity
}