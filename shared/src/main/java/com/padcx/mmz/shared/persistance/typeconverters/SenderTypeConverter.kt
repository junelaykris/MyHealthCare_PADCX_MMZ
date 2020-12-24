package com.padcx.mmz.shared.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcx.mmz.shared.data.vos.SenderVO

/**
 * Created by Myint Myint Zaw on 12/5/2020.
 */
class SenderTypeConverter {
    @TypeConverter
    fun toString(dataList: SenderVO):String{
        return Gson().toJson(dataList)
    }

    @TypeConverter
    fun toList(ListJsonStr:String): SenderVO {
        val dataListType = object : TypeToken<SenderVO>(){}.type
        return Gson().fromJson(ListJsonStr,dataListType)
    }
}
