package com.aidos.doshttpserver.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aidos.doshttpserver.data.CallQueryInfo

@Dao
abstract class CallQueryDao {
    @Query("SELECT * FROM call_info WHERE phoneNumber = :phoneNumber LIMIT 1")
    abstract suspend fun getCallInfoForNumber(phoneNumber: String): CallQueryInfo

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: CallQueryInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: CallQueryInfo)
}