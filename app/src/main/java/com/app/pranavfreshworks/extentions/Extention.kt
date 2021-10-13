package com.app.pranavfreshworks.extentions

import android.app.Activity
import android.app.Dialog
import android.view.KeyEvent
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import com.app.pranavfreshworks.network.NoConnectivityException
import com.app.pranavfreshworks.FreshWorksApp
import com.app.pranavfreshworks.R
import com.app.pranavfreshworks.customview.CustomProgressDialog
import com.app.pranavfreshworks.model.ErrorModel
import com.app.pranavfreshworks.utils.NetworkUtils
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> showInternetDialog(
    activity: Activity,
    call: Call<T>,
    callback: Callback<T>
) {
    if (CustomProgressDialog.getDialogObject() != null && CustomProgressDialog.getDialogObject()
            .isShowing
    ) {
        CustomProgressDialog.dismissDialog()
    }
    if (activity != null) {
        val dialogView = Dialog(activity, R.style.styleFullScreenDialog)
        dialogView.setContentView(R.layout.layout_no_internet_connection)
        dialogView.setCancelable(false)
        val btnRetry = dialogView.findViewById<Button>(R.id.no_internet_connection_btnTryAgain)
        if (!activity.isFinishing) {
            dialogView.show()
        }
        btnRetry.setOnClickListener {
            if (NetworkUtils.isNetworkAvailable(FreshWorksApp.instance.getCurrentActivity())) {

                CustomProgressDialog.showDialog()
//                executeAsync(call, callback)
                call
                    .clone()
                    .enqueue(callback)

                dialogView.dismiss()
            }
        }
        dialogView.setOnKeyListener { arg0, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                btnRetry.performClick()
            }
            true
        }
    }
}

fun <T> onErrorHandling(
    call: Call<T>,
    callback: Callback<T>,
    throwable: Throwable,
    isViewLoading: MutableLiveData<Boolean>,
    onMessageError: MutableLiveData<ErrorModel>
) {
    val errorModel: ErrorModel?

    if (throwable is NoConnectivityException) {
        showInternetDialog(FreshWorksApp.instance.getCurrentActivity(), call, callback)
    } else {
        errorModel = ErrorModel(throwable.hashCode(), throwable.message.toString())

        isViewLoading.postValue(false)
        onMessageError.postValue(errorModel)
    }
}

fun onResponseHandling(
    response: Response<*>,
    isViewLoading: MutableLiveData<Boolean>,
    commonResponse: MutableLiveData<*>,
    onMessageError: MutableLiveData<ErrorModel>
) {

    if (response.isSuccessful && response.body() != null) {
        isViewLoading.postValue(false)
        if (response.body() != null) {
            commonResponse.value = response.body()
        }
    } else {
        val jObjError: JSONObject?
        try {
            jObjError = JSONObject(response.errorBody()!!.string())

            val errorModel =
                ErrorModel(response.code(), jObjError.getString("msg") ?: "")
            isViewLoading.postValue(false)
            onMessageError.postValue(errorModel)
        } catch (e: Exception) {
        }
    }
}