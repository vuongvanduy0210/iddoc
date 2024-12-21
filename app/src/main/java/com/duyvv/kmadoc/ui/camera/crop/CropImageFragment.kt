package com.duyvv.kmadoc.ui.camera.crop

import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseFragment
import com.duyvv.kmadoc.data.model.FormTypeModel
import com.duyvv.kmadoc.databinding.FragmentCropImageBinding
import com.duyvv.kmadoc.ui.camera.CameraFragment.Companion.IMAGE_BITMAP_KEY
import com.duyvv.kmadoc.ui.camera.CameraFragment.Companion.IMAGE_URI_KEY
import com.duyvv.kmadoc.ui.camera.CameraViewModel
import com.duyvv.kmadoc.ui.selectform.SelectFormTypeFragment.Companion.FORM_TYPE_KEY
import com.duyvv.kmadoc.util.fromJson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CropImageFragment : BaseFragment<FragmentCropImageBinding, CameraViewModel>(
    FragmentCropImageBinding::inflate
) {

    override val viewModel: CameraViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.fragment_crop_image
    }

    override fun initView() {
        super.initView()
        viewModel.initData(
            arguments?.getString(IMAGE_BITMAP_KEY).fromJson<Bitmap>(),
            arguments?.getString(IMAGE_URI_KEY)?.let {
                Uri.parse(it)
            },
            arguments?.getString(FORM_TYPE_KEY).fromJson<FormTypeModel>()
        )
    }

    override fun addObserver() {

        viewModel.imageUri.onEach {
            if (it != null) {
            }
        }.launchIn(lifecycleScope)
    }

    override fun addAction() {
        super.addAction()
        binding.btContinue.setOnClickListener {
            /*setFragmentResult(
                key = CROP_IMAGE_KEY,
                value = binding.cropImageView.croppedImage
            )*/
            findNavController().navigateUp()
        }

        binding.btBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    companion object {
        const val CROP_IMAGE_KEY = "CROP_IMAGE_KEY"
    }
}