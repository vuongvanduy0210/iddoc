package com.duyvv.kmadoc.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.PopUpToBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.duyvv.kmadoc.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseNonBindingBottomSheetDialog<ViewModel : BaseViewModel> :
    BottomSheetDialogFragment() {

    override fun onStart() {
        super.onStart()
        val dialog = dialog as? BottomSheetDialog
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BaseBottomSheetDialog)
    }

    fun showDialog(activity: AppCompatActivity) {
        show(activity.supportFragmentManager, null)
    }

    abstract val viewModel: ViewModel

    private var isSetupViewCompleted: Boolean = false
    private var isFullWith = true
    private var isFullHeight = false

    private var manager: WindowManager? = null
    private var metrics: DisplayMetrics? = null

    abstract fun setWidth(): Float

    abstract fun setHeight(): Float

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.let {
            it.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            it.requestFeature(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        addAction()
        addData()
    }

    protected open fun initView() {
    }

    protected open fun addAction() {
    }

    protected open fun addData() {
    }


    /*private fun setupWindowDialog() {
        if (!isSetupViewCompleted) {
            isSetupViewCompleted = true
            val metrics = DisplayMetrics()
            manager?.let { manager ->
                val sizeMathParent = ViewGroup.LayoutParams.MATCH_PARENT.toFloat()
                manager.defaultDisplay.getMetrics(metrics)
                var with = WindowManager.LayoutParams.WRAP_CONTENT
                var height = WindowManager.LayoutParams.WRAP_CONTENT
                val statusBarHeight: Int = getStatusBarHeight(requireActivity())

                if (isFullWith || setWidth() == sizeMathParent) {
                    dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                    with = metrics.widthPixels
                } else {
                    if (setWidth() > 0 && setWidth() < 1) {
                        with = (metrics.widthPixels * setWidth()).toInt()
                    }
                }
                if (isFullHeight || setHeight() == sizeMathParent) {
                    height = sizeMathParent.toInt()
                } else {
                    if (setHeight() > 0 && setHeight() < 1) {
                        height = ((metrics.heightPixels - statusBarHeight) * setHeight()).toInt()
                    }
                }
                dialog?.window?.setLayout(with, height)
            }
        }
    }*/

    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    open fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


    fun toastShort(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun toastLong(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun navigate(
        id: Int,
        extras: Bundle? = null,
        @IdRes popUpTo: Int? = null,
        popUpToBuilder: PopUpToBuilder.() -> Unit = {}
    ) {
        findNavController().navigate(id, extras, navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_right
            }
        })
    }

    fun navigate(
        directions: NavDirections,
        @IdRes popUpTo: Int? = null,
        popUpToBuilder: PopUpToBuilder.() -> Unit = {}
    ) {
        findNavController().navigate(directions, navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_right
            }
        })
    }

    fun navigateUp() {
        parentFragmentManager.popBackStackImmediate()
    }

}
