package com.duyvv.iddoc.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseFragment
import com.duyvv.iddoc.base.BaseViewModel
import com.duyvv.iddoc.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding, BaseViewModel>(
    FragmentSplashBinding::inflate
) {

    override val viewModel: BaseViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.fragment_splash
    }

    override fun initView() {
        super.initView()
        lifecycleScope.launch {
            showLoading()
            delay(2000)
            navigate(R.id.authFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLoading()
    }
}