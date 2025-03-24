package com.aidos.doshttpserver.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aidos.doshttpserver.data.CallQueryInfo

@Database(
    entities = [
        CallQueryInfo::class
    ],
    version = 1,
    exportSchema = false
)

abstract class DosHttpServerDatabase : RoomDatabase() {
    abstract fun callQueryDao(): CallQueryDao
}