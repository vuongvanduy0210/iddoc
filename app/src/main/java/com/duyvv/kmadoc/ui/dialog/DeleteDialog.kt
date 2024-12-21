package com.duyvv.kmadoc.ui.dialog

import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseDialog
import com.duyvv.kmadoc.base.BaseViewModel
import com.duyvv.kmadoc.databinding.DialogDeleteBinding
import com.duyvv.kmadoc.util.Action

class DeleteDialog : BaseDialog<DialogDeleteBinding, BaseViewModel>(
    DialogDeleteBinding::inflate
) {
    override fun setWidth() = 1f

    override fun setHeight() = 1f

    private var onDelete: Action? = null
    override fun getLayoutId(): Int {
        return R.layout.dialog_delete
    }

    override fun addAction() {
        super.addAction()
        binding.btDelete.setOnClickListener {
            onDelete?.invoke()
            dismiss()
        }

        binding.btCancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        fun newInstance(onDelete: Action) = DeleteDialog().apply {
            this.onDelete = onDelete
        }
    }
}