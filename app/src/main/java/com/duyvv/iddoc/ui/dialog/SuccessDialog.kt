package com.duyvv.iddoc.ui.dialog

import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseDialog
import com.duyvv.iddoc.base.BaseViewModel
import com.duyvv.iddoc.databinding.DialogSuccessBinding

class SuccessDialog : BaseDialog<DialogSuccessBinding, BaseViewModel>(
    DialogSuccessBinding::inflate
) {

    override fun setWidth() = 1f

    override fun setHeight() = 1f

    private var title: String? = null
    private var onDismiss: () -> Unit = {}

    override fun getLayoutId(): Int {
        return R.layout.dialog_success
    }

    override fun initView() {
        binding.tvMessage.text = title
    }

    override fun addAction() {
        super.addAction()
        binding.btBack.setOnClickListener {
            onDismiss.invoke()
            dismiss()
        }
    }

    companion object {
        fun newInstance(title: String, onDismiss: () -> Unit): SuccessDialog {
            return SuccessDialog().apply {
                isCancelable = false
                this.title = title
                this.onDismiss = onDismiss
            }
        }
    }
}