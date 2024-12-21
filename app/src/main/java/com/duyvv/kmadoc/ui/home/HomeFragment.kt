package com.duyvv.kmadoc.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseFragment
import com.duyvv.kmadoc.databinding.FragmentHomeBinding
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.gone
import com.duyvv.kmadoc.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override val viewModel: HomeViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
        binding.tvUserName.text = SharePreferenceExt.userInfo.username

        if (SharePreferenceExt.isAdminAccount) {
            binding.btCreateForm.gone()
        } else {
            binding.btCreateForm.visible()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun addObserver() {
        super.addObserver()
        viewModel.listForm.onEach { response ->
            response.let {
                binding.tvFileResultCount.text = "${it.size} đơn"
            }
        }.launchIn(lifecycleScope)
    }

    override fun addAction() {
        super.addAction()

        binding.btSavedTemplate.setOnClickListener {
            navigate(R.id.listFormFragment)
        }

        binding.btCreateForm.setOnClickListener {
            navigate(R.id.selectFormTypeFragment)
        }
    }

    override fun onBackPressed(): Boolean {
        requireActivity().finish()
        return true
    }
}