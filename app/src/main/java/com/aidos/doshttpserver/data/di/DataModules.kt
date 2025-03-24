package com.aidos.doshttpserver.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import com.aidos.doshttpserver.calls.CallLogManager
import com.aidos.doshttpserver.data.currentcalldatastore.CurrentCallDataSource
import com.aidos.doshttpserver.data.currentcalldatastore.CurrentCallSerializer
import com.aidos.doshttpserver.data.currentcalldatastore.DefaultCurrentCallDataSource
import com.aidos.doshttpserver.data.datasource.CallInfoDataSource
import com.aidos.doshttpserver.data.datasource.DefaultCallInfoDataSource
import com.aidos.doshttpserver.data.repository.CallInfoRepository
import com.aidos.doshttpserver.data.repository.DefaultCallInfoRepository
import com.aidos.doshttpserver.data.room.CallQueryDao
import com.aidos.doshttpserver.data.room.DosHttpServerDatabase
import com.aidos.doshttpserver.proto.CurrentCall
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

    @Singleton
    @Binds
    abstract fun bindCurrentCallDataSource(dataSource: DefaultCurrentCallDataSource): CurrentCallDataSource
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


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesCurrentCallDataStore(
        @ApplicationContext context: Context,
        serializer: CurrentCallSerializer,
    ): DataStore<CurrentCall> =
        DataStoreFactory.create(
            serializer = serializer,
        ) {
            context.dataStoreFile("current_call.pb")
        }
}

