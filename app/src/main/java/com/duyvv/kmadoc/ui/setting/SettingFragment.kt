package com.duyvv.kmadoc.ui.setting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseFragment
import com.duyvv.kmadoc.databinding.FragmentSettingBinding
import com.duyvv.kmadoc.ui.auth.login.AuthViewModel
import com.duyvv.kmadoc.util.SharePreferenceExt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, AuthViewModel>(
    FragmentSettingBinding::inflate
) {

    override val viewModel: AuthViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.fragment_setting
    }

    override fun addAction() {
        super.addAction()
        binding.btSignOut.setOnClickListener {
            SharePreferenceExt.username = ""
            SharePreferenceExt.password = ""
            SharePreferenceExt.token = ""

            val navOptions = navOptions {
                popUpTo(R.id.homeFragment) {
                    inclusive = true
                }
                anim {
                    enter = R.anim.slide_in_right
                    popExit = R.anim.slide_out_right
                }
                launchSingleTop = true
            }
            findNavController().navigate(R.id.authFragment, null, navOptions)
        }

        binding.btManageAccount.setOnClickListener {
            toastShort("Tính năng đang phát triển")
        }

        binding.btManageDomain.setOnClickListener {
            findNavController().navigate(R.id.fragmentConfigDomain)
        }

        binding.root.setOnClickListener {
            hideKeyboard(true)
        }
    }
}