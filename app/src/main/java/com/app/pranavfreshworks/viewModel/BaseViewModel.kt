package com.app.pranavfreshworks.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.pranavfreshworks.model.ErrorModel

open class BaseViewModel : ViewModel() {

    val isViewLoading = MutableLiveData<Boolean>()
    val onMessageError = MutableLiveData<ErrorModel>()

}