package com.pmn.data.remote.service

import com.pmn.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {

    private const val DEBUG_BUILD_TYPE = "debug"

    fun makeService(): Service {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(makeOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(Service::class.java)
    }

    private fun makeOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(addHttpLoggingInterceptor())

        return okHttpClient.build()
    }

    @Suppress("KotlinConstantConditions")
    private fun addHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.BUILD_TYPE == DEBUG_BUILD_TYPE)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE

        return logging
    }
}