package com.pmn.baseapplication

import android.app.Application
import com.pmn.baseapplication.di.appModule
import com.pmn.baseapplication.utils.KoinLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            KoinLogger()
            androidContext(this@CustomApplication)
            modules(
                listOf(
                    appModule
                )
            )
        }
    }
}