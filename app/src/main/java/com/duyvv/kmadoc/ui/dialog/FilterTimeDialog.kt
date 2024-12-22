package com.duyvv.kmadoc.ui.dialog

import android.os.Bundle
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseBottomSheetDialog
import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.databinding.DialogDateRangeBinding
import com.duyvv.kmadoc.util.openPickDate

class FilterTimeDialog : BaseBottomSheetDialog<DialogDateRangeBinding, BaseViewModel>(
    DialogDateRangeBinding::inflate
) {
    override fun setWidth() = 1f

    override fun setHeight() = 1f

    private var selectedStartDate: String? = null
    private var selectedEndDate: String? = null

    private var onApply: ((String, String) -> Unit)? = null
    private var onClickCancelFilter: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BaseBottomSheetDialog)
    }

    override fun initView() {
        binding.tvStartDate.text = selectedStartDate
        binding.tvEndDate.text = selectedEndDate
    }

    override fun addAction() {
        super.addAction()
        binding.llStartDate.setOnClickListener {
            openPickDate {
                selectedStartDate = it
                binding.tvStartDate.text = it
            }
        }

        binding.llEndDate.setOnClickListener {
            openPickDate {
                selectedEndDate = it
                binding.tvEndDate.text = it
            }
        }

        binding.btnApply.setOnClickListener {
            onApply?.invoke(selectedStartDate ?: "", selectedEndDate ?: "")
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            onClickCancelFilter?.invoke()
            dismiss()
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        fun newInstance(
            startDate: String,
            endDate: String,
            onClickCancelFilter: () -> Unit,
            onApply: (String, String) -> Unit
        ): FilterTimeDialog {
            return FilterTimeDialog().apply {
                this.selectedStartDate = startDate
                this.selectedEndDate = endDate
                this.onApply = onApply
                this.onClickCancelFilter = onClickCancelFilter
            }
        }
    }
}