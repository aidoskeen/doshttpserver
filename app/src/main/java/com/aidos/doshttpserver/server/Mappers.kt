package com.aidos.doshttpserver.server

import com.aidos.doshttpserver.data.CallLogDataWithTimesQueried
import com.aidos.doshttpserver.data.appconfigdatastore.CurrentCallStatus
import com.aidos.doshttpserver.server.messages.responses.Status
import com.aidos.doshttpserver.server.messages.responses.fieldtype.CallLog

fun CallLogDataWithTimesQueried.toResponseCallLog(): CallLog {
    return CallLog(
        beginning = this.callDate,
        duration = this.callDuration,
        number = this.phNumber,
        name = this.name,
        timesQueried = this.timesQueried
    )
}

fun CurrentCallStatus.toStatus(): Status {
    return Status(
        ongoing = this.ongoing,
        number = this.number,
        name = this.name
    )
}