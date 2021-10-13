package com.app.pranavfreshworks.viewModel

import androidx.lifecycle.MutableLiveData
import com.app.pranavfreshworks.extentions.onErrorHandling
import com.app.pranavfreshworks.extentions.onResponseHandling
import com.app.pranavfreshworks.model.GiphyResponseModel
import com.app.pranavfreshworks.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GiphyViewModel : BaseViewModel() {

    val liveDataGiphyResponse = MutableLiveData<GiphyResponseModel>()
    val liveDataSearchGiphyResponse = MutableLiveData<GiphyResponseModel>()

    fun getTrendingList(body: HashMap<String, String>, isShowProgress: Boolean = true) {

        if (isShowProgress) {
            isViewLoading.postValue(true)
        }

        ApiClient.build().trendingList(body).enqueue(object : Callback<GiphyResponseModel> {
            override fun onFailure(call: Call<GiphyResponseModel>, throwable: Throwable) {
                onErrorHandling(call, this, throwable, isViewLoading, onMessageError)
            }

            override fun onResponse(
                call: Call<GiphyResponseModel>,
                response: Response<GiphyResponseModel>
            ) {
                onResponseHandling(response, isViewLoading, liveDataGiphyResponse, onMessageError)
            }
        })
    }

    fun searchList(body: HashMap<String, String>, isShowProgress: Boolean = true) {

        if (isShowProgress) {
            isViewLoading.postValue(true)
        }

        ApiClient.build().search(body).enqueue(object : Callback<GiphyResponseModel> {
            override fun onFailure(call: Call<GiphyResponseModel>, throwable: Throwable) {
                onErrorHandling(call, this, throwable, isViewLoading, onMessageError)
            }

            override fun onResponse(
                call: Call<GiphyResponseModel>,
                response: Response<GiphyResponseModel>
            ) {
                onResponseHandling(response, isViewLoading, liveDataGiphyResponse, onMessageError)
            }
        })
    }
}