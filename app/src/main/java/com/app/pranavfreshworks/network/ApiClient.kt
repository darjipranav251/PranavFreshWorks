package com.app.pranavfreshworks.network

import com.app.pranavfreshworks.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private var servicesApiInterface: APIInterface? = null

    fun build(): APIInterface {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        httpClient
            .connectTimeout(1, TimeUnit.MINUTES).callTimeout(60, TimeUnit.SECONDS). // 10
            readTimeout(60, TimeUnit.SECONDS).
            writeTimeout(60, TimeUnit.SECONDS).build()

        httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val request =
                original.newBuilder()
                    .method(original.method, original.body)
                    .build()
            chain.proceed(request)
        })

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(httpLoggingInterceptor)
        httpClient.addInterceptor(NetworkConnectionInterceptor())

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            APIInterface::class.java
        )

        return servicesApiInterface as APIInterface
    }
}