package com.duyvv.iddoc.ui.camera.crop

import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseFragment
import com.duyvv.iddoc.data.model.FormTypeModel
import com.duyvv.iddoc.databinding.FragmentCropImageBinding
import com.duyvv.iddoc.ui.camera.CameraFragment.Companion.IMAGE_BITMAP_KEY
import com.duyvv.iddoc.ui.camera.CameraFragment.Companion.IMAGE_URI_KEY
import com.duyvv.iddoc.ui.camera.CameraViewModel
import com.duyvv.iddoc.ui.selectform.SelectFormTypeFragment.Companion.FORM_TYPE_KEY
import com.duyvv.iddoc.util.fromJson
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