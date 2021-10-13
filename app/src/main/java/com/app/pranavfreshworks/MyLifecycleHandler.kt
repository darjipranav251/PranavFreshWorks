package com.app.pranavfreshworks

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MyLifecycleHandler : ActivityLifecycleCallbacks {
    private var resumed = 0
    private var paused = 0
    private var started = 0
    private var stopped = 0
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        if (activity is AppCompatActivity) {
            Companion.activity = activity
        }
    }

    override fun onActivityDestroyed(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {
        ++resumed
        if (activity is AppCompatActivity) {
            Companion.activity = activity
        }
//        Log.e(TAG, "Activity Name Resumed$activity")
    }

    override fun onActivityPaused(activity: Activity) {
        ++paused
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityStarted(activity: Activity) {
        ++started
        numRunningActivities++
        if (activity is AppCompatActivity) {
            Companion.activity = activity
        }
//        Log.e(TAG, "Activity Name Started$activity")
    }

    override fun onActivityStopped(activity: Activity) {
        ++stopped
        numRunningActivities--
    }

    companion object {
        private val TAG = MyLifecycleHandler::class.java.simpleName
        private lateinit var activity: AppCompatActivity
        private var numRunningActivities = 0

        val currentActivity: AppCompatActivity
            get() = activity

        val isAppForeground: Boolean
            get() = numRunningActivities != 0
    }
}