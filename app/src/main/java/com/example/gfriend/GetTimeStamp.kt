package com.example.gfriend

import java.sql.Timestamp
import java.text.SimpleDateFormat

class GetTimeStamp(){
    fun getTimeStamp():Timestamp{
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormatter.parse("2019-03-06 13:28:00")
        return date as Timestamp
    }
}