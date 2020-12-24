package com.padcx.mmz.shared.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcx.mmz.shared.data.vos.DoctorVO

class DoctorVOTypeConverter {
        @TypeConverter
        fun toString(dataList: DoctorVO):String{
            return Gson().toJson(dataList)
        }

        @TypeConverter
        fun toList(ListJsonStr:String): DoctorVO {
            val dataListType = object : TypeToken<DoctorVO>(){}.type
            return Gson().fromJson(ListJsonStr,dataListType)
        }
}