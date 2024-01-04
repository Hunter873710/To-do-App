package com.example.todoapp.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

class DateTypeConverter {
    @TypeConverter
    fun localDateToLong(localDate : LocalDate?): Long? {
        return localDate?.let {
            it.toEpochDay()
        }
    }

    @TypeConverter
    fun longToLocalDate(localDate: Long): LocalDate? {
        return localDate.let {
            LocalDate.ofEpochDay(localDate)
        }
    }
}

class TimeTypeConverter {
    @TypeConverter
    fun timeToLong(localTime: LocalTime?) : Long? {
        return localTime?.let {
            it.toSecondOfDay().toLong()
        }
    }

    @TypeConverter
    fun longToLocalTime(localTime : Long?) : LocalTime? {
        return localTime?.let {
            LocalTime.ofSecondOfDay(localTime)
        }
    }
}
