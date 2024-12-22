package com.duyvv.kmadoc.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDirections
import androidx.navigation.PopUpToBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.R
import androidx.viewbinding.ViewBinding

abstract class BaseDialog<VB : ViewBinding, ViewModel : BaseViewModel>(
    private val bindingInflater: (layoutInflater: LayoutInflater) -> VB
) : DialogFragment() {


    fun showDialog(activity: AppCompatActivity) {
        show(activity.supportFragmentManager, null)
    }

    // Bindings
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    private var isSetupViewCompleted: Boolean = false
    private var isFullWith = false
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

    override fun onStart() {
        super.onStart()
        setupWindowDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        manager = activity?.windowManager
        metrics = DisplayMetrics()
        _binding = bindingInflater.invoke(inflater)
        return binding.root
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


    private fun setupWindowDialog() {
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
    }

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
                enter = R.anim.nav_default_enter_anim
                exit = R.anim.nav_default_exit_anim
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
                enter = R.anim.nav_default_enter_anim
                exit = R.anim.nav_default_exit_anim
            }
        })
    }

    fun navigateUp() {
        parentFragmentManager.popBackStackImmediate()
    }

}
