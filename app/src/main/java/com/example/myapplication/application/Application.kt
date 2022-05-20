package com.example.myapplication.application

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner

class Application :Application() {


    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        private var instance: com.example.myapplication.application.Application? = null
        val context: Context?
            get() = instance
    }
}