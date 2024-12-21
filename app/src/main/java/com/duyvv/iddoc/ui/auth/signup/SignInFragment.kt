package com.duyvv.iddoc.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseNonBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignInFragment : BaseNonBindingFragment<SignInViewModel>() {
    override val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SignUpScreen(
                    viewModel = viewModel,
                    onClickSignUp = {
                        viewModel.signUp()
                    },
                    onClickConfigDomain = {
                        navigate(R.id.fragmentConfigDomain)
                    },
                    onClickBack = {
                        findNavController().navigateUp()
                    }
                )
            }
        }
    }

    override fun addAction() {
        super.addAction()
        /*binding.btBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btSignIn.setOnClickListener {
            viewModel.signUp()
        }

        binding.btConfigDomain.setOnClickListener {
            navigate(R.id.fragmentConfigDomain)
        }*/
    }

    override fun addObserver() {
        super.addObserver()

        /*binding.edtLayoutStudentCode.editText?.addTextChangedListener {
            viewModel.updateStudentCode(it?.trim().toString())
        }

        binding.edtLayoutPassword.editText?.addTextChangedListener {
            viewModel.updatePassword(it?.trim().toString())
        }

        binding.edtLayoutRePassword.editText?.addTextChangedListener {
            viewModel.updateConfirmPassword(it?.trim().toString())
        }

        binding.edtLayoutName.editText?.addTextChangedListener {
            viewModel.updateUserName(it?.trim().toString())
        }

        viewModel.isValidInput.onEach {
            binding.btSignIn.apply {
                isEnabled = it
            }
        }.launchIn(lifecycleScope)*/

        viewModel.signUpResponse.onEach {
            it?.let {
                Toasty.success(requireContext(), "Đăng ký thành công").show()
                findNavController().navigateUp()
            }
        }.launchIn(lifecycleScope)
    }
}