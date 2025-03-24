package com.aidos.doshttpserver.server

import com.aidos.doshttpserver.data.CallLogData
import com.aidos.doshttpserver.data.currentcalldatastore.CurrentCallStatus
import com.aidos.doshttpserver.proto.CurrentCall
import com.aidos.doshttpserver.server.messages.responses.Status
import com.aidos.doshttpserver.server.messages.responses.fieldtype.CallLog

fun CallLogData.toCallLog(): CallLog {
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