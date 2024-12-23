package com.duyvv.kmadoc.ui.setting

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseFragment
import com.duyvv.kmadoc.data.model.SettingsAction
import com.duyvv.kmadoc.databinding.FragmentSettingBinding
import com.duyvv.kmadoc.ui.auth.login.AuthViewModel
import com.duyvv.kmadoc.util.SharePreferenceExt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, AuthViewModel>(
    FragmentSettingBinding::inflate
) {

    private val actionAdapter: SettingsActionAdapter by lazy {
        SettingsActionAdapter { position, it ->
            handleClickItem(position, it)
        }
    }

    private fun handleClickItem(position: Int, item: SettingsAction) {
        when (position) {
            0 -> {
                navigate(R.id.changeInfoFragment)
            }

            1 -> {
                navigate(R.id.changePassFragment)
            }

            2 -> {
                navigate(R.id.fragmentConfigDomain)
            }

            3 -> {
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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override val viewModel: AuthViewModel by viewModels()

    override fun initView() {
        binding.rcvItem.apply {
            adapter = actionAdapter
        }
        actionAdapter.submitList(
            listOf(
                SettingsAction(R.drawable.ic_user, "Quản lý thông tin cá nhân"),
                SettingsAction(R.drawable.ic_password_bold, "Đổi mật khẩu"),
                SettingsAction(R.drawable.ic_key_manage, "Quản lý domain"),
                SettingsAction(R.drawable.ic_exit, "Đăng xuất"),
            )
        )
    }

    override fun addAction() {
        super.addAction()
        binding.root.setOnClickListener {
            hideKeyboard(true)
        }
    }
}