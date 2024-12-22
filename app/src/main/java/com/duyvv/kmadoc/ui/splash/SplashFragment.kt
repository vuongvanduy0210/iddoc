package com.duyvv.kmadoc.ui.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseFragment
import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding, BaseViewModel>(
    FragmentSplashBinding::inflate
) {

    override val viewModel: BaseViewModel by viewModels()

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