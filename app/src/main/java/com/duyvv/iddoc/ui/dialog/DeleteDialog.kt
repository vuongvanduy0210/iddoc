package com.duyvv.iddoc.ui.dialog

import com.duyvv.iddoc.R
import com.duyvv.iddoc.base.BaseDialog
import com.duyvv.iddoc.base.BaseViewModel
import com.duyvv.iddoc.databinding.DialogDeleteBinding
import com.duyvv.iddoc.util.Action

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