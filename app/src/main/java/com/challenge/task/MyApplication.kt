package com.challenge.task

import android.app.Application
import com.challenge.task.di.AppComponent
import com.challenge.task.di.DaggerAppComponent
import com.challenge.task.di.modules.AppModule
import timber.log.Timber

class MyApplication : Application() {

    val component: AppComponent by lazy { createBuilder().build() }

    private fun createBuilder(): AppComponent.Builder {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
