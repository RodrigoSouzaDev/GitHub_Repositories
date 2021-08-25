package com.example.github_repositories

import android.app.Application
import com.example.github_repositories.data.di.DataModule
import com.example.github_repositories.domain.di.DomainModule
import com.example.github_repositories.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App(): Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            DataModule.load()
            DomainModule.load()
            PresentationModule.load()
        }


    }
}