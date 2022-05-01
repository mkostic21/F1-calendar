package com.example.f1_calendar.util

import androidx.room.ProvidedTypeConverter
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.ZonedDateTime

@ProvidedTypeConverter
class ZonedDateTimeAdapter : TypeAdapter<ZonedDateTime>() {
    override fun write(out: JsonWriter?, value: ZonedDateTime?) {
        if(value == null){
            out?.nullValue()
            return
        }
        val jsonString = value.toString()
        out?.value(jsonString)
    }

    override fun read(`in`: JsonReader?): ZonedDateTime {
        return ZonedDateTime.parse(`in`?.nextString())
    }
}