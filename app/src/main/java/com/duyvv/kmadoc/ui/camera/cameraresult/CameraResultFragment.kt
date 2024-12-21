package com.duyvv.kmadoc.ui.camera.cameraresult

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseFragment
import com.duyvv.kmadoc.base.mvi.MviEffect
import com.duyvv.kmadoc.data.model.FormTypeModel
import com.duyvv.kmadoc.databinding.FragmentCameraResultBinding
import com.duyvv.kmadoc.ui.camera.CameraContract
import com.duyvv.kmadoc.ui.camera.CameraFragment.Companion.IMAGE_BITMAP_KEY
import com.duyvv.kmadoc.ui.camera.CameraFragment.Companion.IMAGE_URI_KEY
import com.duyvv.kmadoc.ui.camera.CameraViewModel
import com.duyvv.kmadoc.ui.demoinfo.DemoInfoFragment.Companion.SCREEN_TYPE_KEY
import com.duyvv.kmadoc.ui.demoinfo.DemoScreenType
import com.duyvv.kmadoc.ui.selectform.SelectFormTypeFragment.Companion.FORM_TYPE_KEY
import com.duyvv.kmadoc.util.fromJson
import com.duyvv.kmadoc.util.toJson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraResultFragment : BaseFragment<FragmentCameraResultBinding, CameraViewModel>(
    FragmentCameraResultBinding::inflate
) {

    override val viewModel: CameraViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.fragment_camera_result
    }

    override fun initView() {
        viewModel.initData(
            arguments?.getString(IMAGE_BITMAP_KEY).fromJson<Bitmap>(),
            arguments?.getString(IMAGE_URI_KEY)?.let {
                Uri.parse(it)
            },
            arguments?.getString(FORM_TYPE_KEY).fromJson<FormTypeModel>()
        )
    }

    override fun handleEffect(effect: MviEffect) {
        when (effect) {
            is CameraContract.CameraEffect.OcrImageSuccess -> {
                navigate(
                    R.id.demoInfoFragment,
                    bundleOf(
                        FORM_TYPE_KEY to viewModel.formTypeModel.toJson(),
                        FORM_KEY to effect.formModel.toJson(),
                        SCREEN_TYPE_KEY to DemoScreenType.TYPE_UPLOAD.toJson()
                    )
                )
            }
        }
    }

    override fun addObserver() {

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun addAction() {
        super.addAction()
        binding.btBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btContinue.setOnClickListener {
            if (viewModel.formTypeModel != null) {
                showLoading()
                viewModel.ocr(requireContext(), viewModel.formTypeModel!!)
            }
        }

        binding.btEdit.setOnClickListener {
            navigate(
                R.id.cropImageFragment,
                bundleOf(
                    IMAGE_URI_KEY to viewModel.imageUri.value.toString(),
                    FORM_TYPE_KEY to viewModel.formTypeModel.toJson()
                )
            )
        }

        binding.ivResult.setOnClickListener {

        }
    }

    companion object {
        const val FORM_KEY = "FORM_KEY"
    }
}