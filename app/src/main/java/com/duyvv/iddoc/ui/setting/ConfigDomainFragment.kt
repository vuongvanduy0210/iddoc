package com.duyvv.iddoc.ui.setting

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseFragment
import com.duyvv.iddoc.base.BaseViewModel
import com.duyvv.iddoc.databinding.FragmentConfigDomainBinding
import com.duyvv.iddoc.util.SharePreferenceExt
import com.duyvv.iddoc.util.showSuccessDialog
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