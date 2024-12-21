package com.duyvv.iddoc

import android.app.Application
import com.cloudinary.android.MediaManager
import com.duyvv.iddoc.util.SharePreferenceExt
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharePreferenceExt.SharePreferences.init(this)

        val config: HashMap<String, String> = hashMapOf(
            "cloud_name" to "dog8piybp",
            "api_key" to "327437699574223",
            "api_secret" to "ftTMGa56Ma9-W3PZGA0t6z3TtvM"
        )
        MediaManager.init(this, config)
    }
}