package com.onebyte.tagmoo.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.onebyte.tagmoo.BR
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import androidx.core.content.ContextCompat
import android.view.WindowManager
import com.onebyte.tagmoo.R


@Suppress("UNUSED_PARAMETER")
abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel> : DaggerAppCompatActivity(), BaseFragment.Callback {
    protected lateinit var binding: B
    lateinit var viewModel: VM

    abstract fun getViewModelClass(): Class<VM>

    @LayoutRes
    protected abstract fun layoutId(): Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindContentView(layoutId())
        statusBarColor()
    }

    private fun statusBarColor() {
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorAccent)
    }

    private fun bindContentView(layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModelClass())
        binding.setVariable(BR.viewModel, viewModel)
    }

    fun hideKeyboard() {
        try {
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (ex: Exception){
            Log.e("HIDE_KEYBOARD", "$ex")
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}