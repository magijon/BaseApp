package com.mgijon.baseapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.mgijon.baseapp.example.activity.BaseActivity
import com.mgijon.baseapp.example.activity.MainActivity
import com.mgijon.baseapp.example.model.StateBase
import com.mgijon.domain.common.Resource

abstract class BaseFragment<V : BaseViewModel, B : ViewBinding> : Fragment() {

    abstract val viewModel: V
    lateinit var binding: B

    abstract fun getViewBinding(): B
    abstract fun initViews()
    abstract fun initObservers()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startLogic(arguments)
        initViews()
        initObservers()
    }

    protected fun <T : StateBase> runGenericState(state: LiveData<T>, onError: (() -> Unit)? = null, onSuccess: () -> Unit) {
        state.observe(viewLifecycleOwner){
            when{
                it.isLoading -> { showProgress() }
                it.error?.isError == true -> {
                    hideProgress()
                    onError?.invoke() ?: kotlin.run { showGenericErrorFragment() }}
                else -> {
                    hideProgress()
                    onSuccess.invoke() }
            }
        }
    }

    private fun hideProgress() {
        (activity as BaseActivity).loading(false)
    }

    private fun showProgress() {
        (activity as BaseActivity).loading(true)
    }

    private fun showGenericErrorFragment() {
        //TODO
    }
}