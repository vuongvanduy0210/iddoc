package com.duyvv.iddoc.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseNonBindingFragment
import com.duyvv.iddoc.base.mvi.MviEffect
import com.duyvv.iddoc.ui.auth.AuthContract
import com.duyvv.iddoc.util.SharePreferenceExt
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty

@AndroidEntryPoint
class AuthFragment : BaseNonBindingFragment<AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AuthScreen(
                    viewModel = viewModel,
                    onClickLogin = {
                        viewModel.login()
                    },
                    onClickSignUp = {
                        navigate(id = R.id.signInFragment)
                    }
                )
            }
        }
    }

    override fun addAction() {
        super.addAction()
        /*binding.btLogin.setOnClickListener {
            viewModel.login(binding.checkboxAdmin.isChecked)
        }

        binding.btSignIn.setOnClickListener {
            navigate(id = R.id.signInFragment)
        }*/
    }

    override fun initView() {
        /*viewModel.isValidateInput.onEach {
            binding.btLogin.apply {
                isEnabled = it
            }
        }.launchIn(lifecycleScope)*/

//        binding.edtLayoutUserName.editText?.setText(SharePreferenceExt.username)
    }

    override fun handleEffect(effect: MviEffect) {
        when (effect) {
            is AuthContract.AuthEffect.LoginSuccess -> {
                effect.response.let {
                    if (!it.info?.accessToken.isNullOrEmpty()) {
                        SharePreferenceExt.token = it.info?.accessToken ?: ""
                        SharePreferenceExt.username = viewModel.userName.value
                        SharePreferenceExt.password = viewModel.password.value
                        Toasty.success(requireContext(), "Đăng nhập thành công").show()
                        val navOptions = androidx.navigation.navOptions {
                            popUpTo(R.id.authFragment) {
                                inclusive = true
                            }
                            anim {
                                enter = R.anim.slide_in_right
                                popExit = R.anim.slide_out_right
                            }
                            launchSingleTop = true
                        }
                        findNavController().navigate(R.id.homeFragment, null, navOptions)
                    }
                }
            }
        }
    }

    override fun addObserver() {
        super.addObserver()
        /*binding.edtLayoutUserName.editText?.addTextChangedListener {
            viewModel.updateUserName(it?.trim().toString())
        }
        binding.edtLayoutPassword.editText?.addTextChangedListener {
            viewModel.updatePassword(it?.trim().toString())
        }*/
    }
}