package com.aidos.doshttpserver

import android.app.Application
import com.aidos.doshttpserver.data.appconfigdatastore.AppConfiguration
import com.aidos.doshttpserver.data.repository.AppConfigRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import javax.inject.Inject

@HiltAndroidApp
class DosHttpServerApp: Application() {
    @Inject
    lateinit var appConfigRepository: AppConfigRepository

    override fun onCreate() {
        super.onCreate()
        runBlocking { appConfigRepository.setFirstLaunchTime(System.currentTimeMillis().toString()) }
    }
}