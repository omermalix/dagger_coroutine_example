package com.onebyte.tagmoo.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.onebyte.tagmoo.BR
import dagger.android.support.DaggerFragment

@Suppress("UNUSED_PARAMETER")
abstract class BaseFragment<T: ViewDataBinding, V: BaseViewModel> : DaggerFragment() {

    protected abstract val layoutId: Int
    var mActivity: BaseActivity<*, *>? = null

    private var binding: T? = null
    private var mViewModel: V? = null
    private var mRootView: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity: BaseActivity<*, *> = context
            this.mActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            mRootView = binding!!.root
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.setVariable(BR.viewModel, mViewModel)
        binding!!.lifecycleOwner = this
        binding!!.executePendingBindings()
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}