package com.aidos.doshttpserver.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "call_info",
)
data class CallWithTimesQueried(
    @PrimaryKey(autoGenerate = false)
    val phoneNumber: String,
    val timesQueried: Int
)