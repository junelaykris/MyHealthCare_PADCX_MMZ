package com.padcx.mmz.shared.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcx.mmz.shared.data.vos.DeliveryRoutineVO

class DeliveryRoutineTypeConverter {
    @TypeConverter
    fun toString(dataList: DeliveryRoutineVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): DeliveryRoutineVO {
        val dataListType = object : TypeToken<DeliveryRoutineVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}