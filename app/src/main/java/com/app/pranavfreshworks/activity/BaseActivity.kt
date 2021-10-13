package com.app.pranavfreshworks.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.pranavfreshworks.customview.CustomProgressDialog
import com.app.pranavfreshworks.model.ErrorModel
import com.app.pranavfreshworks.utils.MessageDialog
import com.app.pranavfreshworks.viewModel.BaseViewModel

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun defineLayoutResource(): Int

    protected abstract fun initializeComponents()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(defineLayoutResource())

        initializeComponents()
    }
}