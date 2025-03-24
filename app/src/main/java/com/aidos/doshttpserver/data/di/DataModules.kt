package com.aidos.doshttpserver.data.di

import android.content.Context
import androidx.room.Room
import com.aidos.doshttpserver.calls.CallLogManager
import com.aidos.doshttpserver.data.datasource.CallInfoDataSource
import com.aidos.doshttpserver.data.datasource.DefaultCallInfoDataSource
import com.aidos.doshttpserver.data.repository.CallInfoRepository
import com.aidos.doshttpserver.data.repository.DefaultCallInfoRepository
import com.aidos.doshttpserver.data.room.CallQueryDao
import com.aidos.doshttpserver.data.room.DosHttpServerDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCallInfoRepository(repository: DefaultCallInfoRepository): CallInfoRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindCallInfoDataSource(dataSource: DefaultCallInfoDataSource): CallInfoDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): DosHttpServerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DosHttpServerDatabase::class.java,
            "Calls.db"
        ).build()
    }

    @Provides
    fun provideCallQueryDao(database: DosHttpServerDatabase): CallQueryDao = database.callQueryDao()
}

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {
    @Provides
    fun providesCallLogManager(@ApplicationContext context: Context): CallLogManager = CallLogManager(context)
}


