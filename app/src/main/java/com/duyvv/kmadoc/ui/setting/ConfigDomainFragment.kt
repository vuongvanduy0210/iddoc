package com.duyvv.kmadoc.ui.setting

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseFragment
import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.databinding.FragmentConfigDomainBinding
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.showSuccessDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConfigDomainFragment : BaseFragment<FragmentConfigDomainBinding, BaseViewModel>(
    FragmentConfigDomainBinding::inflate
) {

    override val viewModel: BaseViewModel by viewModels()

    @Inject
    override fun getLayoutId(): Int {
        return R.layout.fragment_config_domain
    }

    override fun initView() {
        super.initView()
        Log.d("ConfigDomainFragment", SharePreferenceExt.lastDomain)
        binding.edtIpServer.setText(SharePreferenceExt.lastDomain)
    }

    override fun addAction() {
        super.addAction()
        binding.btBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btServer.setOnClickListener {
            SharePreferenceExt.lastDomain = (binding.edtIpServer.text ?: "").toString()
            showSuccessDialog("Config domain thành công: ${SharePreferenceExt.lastDomain}") {
                findNavController().navigateUp()
            }
        }
    }

}