package com.aidos.doshttpserver.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aidos.doshttpserver.data.CallWithTimesQueried

@Dao
abstract class CallQueryDao {
    @Query("SELECT * FROM call_info WHERE phoneNumber = :phoneNumber LIMIT 1")
    abstract suspend fun getCallInfoForNumber(phoneNumber: String): CallWithTimesQueried?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: CallWithTimesQueried)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: CallWithTimesQueried)
}