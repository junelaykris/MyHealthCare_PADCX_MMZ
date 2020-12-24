package com.padcx.mmz.shared.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.data.vos.RoutineVO

class PrescriptionTypeConverters {
    @TypeConverter
    fun toString(dataList: ArrayList<PrescriptionVO>):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): ArrayList<PrescriptionVO> {
        val dataListType = object : TypeToken<ArrayList<RoutineVO>>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}
