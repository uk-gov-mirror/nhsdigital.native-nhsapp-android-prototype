package com.prototype.demonhsapp

import android.app.Application
import com.prototype.demonhsapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class NHSApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NHSApp)
        }
    }
}