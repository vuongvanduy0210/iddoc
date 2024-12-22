package com.duyvv.kmadoc.ui.home

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.duyvv.kmadoc.R
import com.duyvv.kmadoc.base.BaseActivity
import com.duyvv.kmadoc.databinding.ActivityMainBinding
import com.duyvv.kmadoc.util.SharePreferenceExt
import com.duyvv.kmadoc.util.animateViewFromBottom
import com.duyvv.kmadoc.util.gone
import com.duyvv.kmadoc.util.hideViewToTop
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var navController: NavController

    override fun createBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    private var isShowBottomNav: Boolean? = null

    override fun initView() {
        super.initView()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController

        val graph = navController.navInflater.inflate(R.navigation.app_nav)
        val token = SharePreferenceExt.token
        if (token.isNotEmpty()) {
            graph.setStartDestination(R.id.homeFragment)
        } else {
            graph.setStartDestination(R.id.authFragment)
        }
        navController.setGraph(graph, null)
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun addObserver() {
        super.addObserver()
        navController.addOnDestinationChangedListener { nav, des, extras ->
            val shouldShowBottomNav = des.id in listOf(R.id.homeFragment, R.id.settingFragment)
            if (isShowBottomNav != shouldShowBottomNav) {
                if (shouldShowBottomNav) {
                    binding.bottomNav.animateViewFromBottom()
                    binding.btScan.animateViewFromBottom()
                } else {
                    binding.bottomNav.hideViewToTop()
                    binding.btScan.hideViewToTop()
                }
                isShowBottomNav = shouldShowBottomNav
            }
        }
    }

    override fun addAction() {
        super.addAction()
        binding.btScan.setOnClickListener {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            navController = navHostFragment!!.navController
            navController.navigate(
                R.id.selectFormTypeFragment,
                bundleOf(
                    IS_FROM_CAMERA to true
                ),
                navOptions {
                    anim {
                        enter = R.anim.slide_in_right
                        popExit = R.anim.slide_out_right
                    }
                }
            )
            binding.flowBtScan.gone()
        }

        binding.flowBtScan.setOnClickListener {
            binding.flowBtScan.gone()
        }
    }


    companion object {
        const val IS_FROM_CAMERA = "isFromCamera"
    }
}