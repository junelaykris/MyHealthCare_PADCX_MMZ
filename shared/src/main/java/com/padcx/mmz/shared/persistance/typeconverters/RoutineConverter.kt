package com.padcx.mmz.shared.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcx.mmz.shared.data.vos.RoutineVO

class RoutineConverter {
    @TypeConverter
    fun toString(dataList: RoutineVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): RoutineVO {
        val dataListType = object : TypeToken<RoutineVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}