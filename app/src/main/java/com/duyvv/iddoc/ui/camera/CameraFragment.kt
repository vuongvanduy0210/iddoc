package com.duyvv.iddoc.ui.camera

import android.app.Activity.RESULT_OK
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseFragment
import com.duyvv.iddoc.base.mvi.MviEffect
import com.duyvv.iddoc.data.model.FormTypeModel
import com.duyvv.iddoc.databinding.FragmentCameraBinding
import com.duyvv.iddoc.ui.camera.cameraresult.CameraResultFragment.Companion.FORM_KEY
import com.duyvv.iddoc.ui.demoinfo.DemoInfoFragment.Companion.SCREEN_TYPE_KEY
import com.duyvv.iddoc.ui.demoinfo.DemoScreenType
import com.duyvv.iddoc.ui.selectform.SelectFormTypeFragment.Companion.FORM_TYPE_KEY
import com.duyvv.iddoc.util.fromJson
import com.duyvv.iddoc.util.toJson
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CameraFragment : BaseFragment<FragmentCameraBinding, CameraViewModel>(
    FragmentCameraBinding::inflate
) {

    override val viewModel: CameraViewModel by viewModels()

    private val scannerLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            GmsDocumentScanningResult.fromActivityResultIntent(result.data).let { it ->
                viewModel.updateUri(it?.pages?.map { it.imageUri }?.first())
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_camera
    }

    override fun initView() {
        super.initView()
        viewModel.formTypeModel =
            arguments?.getString(FORM_TYPE_KEY).fromJson<FormTypeModel>()
    }

    private fun startCameraView() {
        val option = GmsDocumentScannerOptions.Builder()
            .setScannerMode(SCANNER_MODE_FULL)
            .setGalleryImportAllowed(true)
            .setPageLimit(1)
            .setResultFormats(RESULT_FORMAT_JPEG)
            .build()
        val scanner = GmsDocumentScanning.getClient(option)
        scanner.getStartScanIntent(requireActivity())
            .addOnSuccessListener {
                scannerLauncher.launch(
                    IntentSenderRequest.Builder(it).build()
                )
            }.addOnFailureListener {
                Toasty.error(requireContext(), it.message.toString()).show()
            }
    }

    override fun addObserver() {
        viewModel.imageUri.onEach {
            if (it != null) {
                binding.imageView.setImageURI(it)
            }
        }.launchIn(lifecycleScope)
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

    override fun addAction() {
        super.addAction()
        binding.btBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btScan.setOnClickListener {
            startCameraView()
        }

        binding.btContinue.setOnClickListener {
            if (viewModel.formTypeModel != null) {
                if (viewModel.imageUri.value == null) {
                    Toasty.warning(requireContext(), "Vui lòng chụp ảnh trước khi tiếp tục").show()
                    return@setOnClickListener
                }
                viewModel.ocr(requireContext(), viewModel.formTypeModel!!)
            }
        }
    }

    companion object {
        const val IMAGE_BITMAP_KEY = "IMAGE_BITMAP_KEY"
        const val IMAGE_URI_KEY = "IMAGE_URI_KEY"
    }
}