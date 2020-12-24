package com.padcx.mmz.shared.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcx.mmz.shared.data.vos.PatientVO

class PatientVOTypeConverter {
    @TypeConverter
    fun toString(dataList: PatientVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): PatientVO {
        val dataListType = object : TypeToken<PatientVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}