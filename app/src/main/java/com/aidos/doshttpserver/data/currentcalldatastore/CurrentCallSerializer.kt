package com.aidos.doshttpserver.data.currentcalldatastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.aidos.doshttpserver.proto.CurrentCall
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class CurrentCallSerializer @Inject constructor(): Serializer<CurrentCall> {
    override val defaultValue: CurrentCall = CurrentCall.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CurrentCall =
        try {
            // readFrom is already called on the data store background thread
            CurrentCall.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: CurrentCall, output: OutputStream) {
        t.writeTo(output)
    }
}
