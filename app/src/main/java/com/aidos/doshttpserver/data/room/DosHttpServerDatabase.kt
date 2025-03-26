package com.aidos.doshttpserver.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aidos.doshttpserver.data.CallWithTimesQueried

@Database(
    entities = [
        CallWithTimesQueried::class
    ],
    version = 1,
    exportSchema = false
)

abstract class DosHttpServerDatabase : RoomDatabase() {
    abstract fun callQueryDao(): CallQueryDao
}