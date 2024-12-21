package com.duyvv.iddoc.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.PopUpToBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.mvi.MviEffect
import com.duyvv.iddoc.base.mvi.MviState
import es.dmoral.toasty.Toasty
import io.github.rupinderjeet.kprogresshud.KProgressHUD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseNonBindingFragment<viewModel : BaseViewModel> : Fragment() {
    private var hud: KProgressHUD? = null

    protected abstract val viewModel: viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        observerEvents()
        addObserver()
        addAction()
        initData()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (onBackPressed()) {
                        return
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observerEvents() {
        viewModel.isLoading.receiveAsFlow().mapLatest {
            if (it) {
                showLoading()
            } else {
                dismissLoading()
            }
        }.launchIn(viewModel.viewModelScopeExceptionHandler)

        viewModel.errorMessage.receiveAsFlow().mapLatest {
            if (it.isNotEmpty()) {
                Toasty.error(requireContext(), it, Toasty.LENGTH_SHORT, true).show()
            }
        }.launchIn(viewModel.viewModelScopeExceptionHandler)

        viewModel.mviState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { mviState ->
                handleState(mviState)
            }.launchIn(viewModel.viewModelScopeExceptionHandler)

        viewModel.mviEffect.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { mviEffect ->
                handleEffect(mviEffect)
            }.launchIn(viewModel.viewModelScopeExceptionHandler)
    }

    protected open fun handleEffect(effect: MviEffect) = Unit
    protected open fun handleState(state: MviState) = Unit
    protected open fun initViewModel() {}
    protected open fun addObserver() {}
    protected open fun initView() {}
    protected open fun addAction() {}
    open fun initData() {}
    open fun onBackPressed(): Boolean {
        return false
    }


    open fun finish() {
        parentFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }

    fun finishAllowingStateLoss() {
        parentFragmentManager.beginTransaction()
            .remove(this)
            .commitAllowingStateLoss()
    }

    fun showChildFragment(child: Fragment, container: Int, name: String) {
        childFragmentManager.beginTransaction()
            .replace(container, child, name)
            .commit()
    }

    fun toastShort(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun toastLong(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun removeChildFragment(name: String) {
        val child = childFragmentManager.findFragmentByTag(name)
        if (child != null) {
            childFragmentManager.beginTransaction().remove(child).commit()
        }
    }

    fun navigate(
        id: Int,
        extras: Bundle? = null,
        launchSingleTop: Boolean = true,
        @IdRes popUpTo: Int? = null,
        popUpToBuilder: PopUpToBuilder.() -> Unit = {}
    ) {
        findNavController().navigate(id, extras, navOptions {
            anim {
                enter = R.anim.slide_in_right
                popExit = R.anim.slide_out_right
            }
            this.launchSingleTop = launchSingleTop
            popUpTo?.let { popUpTo(popUpTo, popUpToBuilder) }
        })
    }

    fun navigate(
        directions: NavDirections,
        @IdRes popUpTo: Int? = null,
        popUpToBuilder: PopUpToBuilder.() -> Unit = {}
    ) {
        findNavController().navigate(directions, navOptions {
            anim {
                enter = R.anim.slide_in_right
                popExit = R.anim.slide_out_right
            }
            popUpTo?.let { popUpTo(popUpTo, popUpToBuilder) }
        })
    }

    fun navigateUp() {
        parentFragmentManager.popBackStackImmediate()
    }

    open fun hideKeyboard(clearFocusView: Boolean = true) {
        (activity as? BaseActivity<*>)?.hideKeyboard(clearFocusView)
    }

    private fun showLoading(waitText: String = "Please wait") {
        hud = KProgressHUD.create(requireActivity())
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel(waitText)
            .setCancellable(true)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .show()
    }

    private fun dismissLoading() {
        hud?.dismiss()
        hud = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoading()
    }
}