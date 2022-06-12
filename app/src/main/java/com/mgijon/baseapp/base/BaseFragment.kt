package com.mgijon.baseapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.mgijon.baseapp.example.model.StateBase
import com.mgijon.baseapp.example.model.TitleFragmentType

abstract class BaseFragment<V : BaseViewModel, B : ViewBinding, A : AppCompatActivity> :
    Fragment() {

    abstract val viewModel: V
    lateinit var binding: B

    abstract fun getViewBinding(): B
    abstract fun initViews()
    abstract fun initObservers()

    open val title: Int = TitleFragmentType.NOT_TITLE.value

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

    override fun onResume() {
        super.onResume()
        when(title) {
            TitleFragmentType.NOT_TITLE.value -> {(activity as BaseActivity<*>).hideTitle()}
            TitleFragmentType.DYNAMIC_TITLE.value -> {}
            else -> {setTitle(getString(title))}
        }
    }

    protected fun <T : StateBase> runGenericState(state: LiveData<T>, onError: (() -> Unit)? = null, onSuccess: () -> Unit) {
        state.observe(viewLifecycleOwner) {
            when {
                it.isLoading -> {
                    showProgress()
                }
                it.error?.isError == true -> {
                    hideProgress()
                    onError?.invoke() ?: kotlin.run { showGenericErrorFragment() }
                }
                else -> {
                    hideProgress()
                    onSuccess.invoke()
                }
            }
        }
    }

    private fun hideProgress() {
        (activity as BaseActivity<*>).loading(false)
    }

    private fun showProgress() {
        (activity as BaseActivity<*>).loading(true)
    }

    private fun showGenericErrorFragment() {
        //TODO
    }

    fun setTitle(string: String?) {
        string?.let {
            (activity as BaseActivity<*>).setTitleStatusBar(it)
        }
    }
}