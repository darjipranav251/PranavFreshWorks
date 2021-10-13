package com.app.pranavfreshworks

import android.app.Activity
import android.app.Application
import io.paperdb.Paper

class FreshWorksApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Paper.init(this)
        myLifecycleHandler = MyLifecycleHandler()
        registerActivityLifecycleCallbacks(myLifecycleHandler)
    }

    companion object {

        lateinit var instance: FreshWorksApp
        lateinit var myLifecycleHandler: MyLifecycleHandler
    }

    fun getCurrentActivity(): Activity {
        return MyLifecycleHandler.currentActivity
    }
}