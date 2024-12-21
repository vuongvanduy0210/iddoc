package com.duyvv.iddoc.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseFragment
import com.duyvv.iddoc.databinding.FragmentHomeBinding
import com.duyvv.iddoc.util.SharePreferenceExt
import com.duyvv.iddoc.util.gone
import com.duyvv.iddoc.util.visible
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