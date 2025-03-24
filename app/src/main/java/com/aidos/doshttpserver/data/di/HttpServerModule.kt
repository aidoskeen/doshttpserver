package com.aidos.doshttpserver.data.di

import com.aidos.doshttpserver.data.repository.CallInfoRepository
import com.aidos.doshttpserver.server.HttpServer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HttpServerModule {
    @Provides
    fun providesHttpServer(callInfoRepository: CallInfoRepository): HttpServer = HttpServer(callInfoRepository)
}