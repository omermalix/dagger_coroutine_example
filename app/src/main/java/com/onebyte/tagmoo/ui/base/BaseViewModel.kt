package com.onebyte.tagmoo.ui.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

  override fun onCleared() {
    super.onCleared()
  }
}
