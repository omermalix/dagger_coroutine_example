package com.onebyte.tagmoo.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onebyte.tagmoo.ui.cameraView.CameraViewModel
import com.onebyte.tagmoo.ui.takePictureView.TakePictureViewModel
import com.onebyte.tagmoo.viewModelFactory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CameraViewModel::class)
    internal abstract fun bindCameraViewModel(cameraViewModel: CameraViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TakePictureViewModel::class)
    internal abstract fun bindTakePictureViewModel(takePictureViewModel: TakePictureViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}