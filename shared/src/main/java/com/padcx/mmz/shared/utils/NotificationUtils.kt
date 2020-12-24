package com.padcx.mmz.shared.utils

import android.content.Context
import com.padcx.mmz.shared.R

import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.network.responses.DataVO
import com.padcx.mmz.shared.network.responses.NotificationVO


fun prepareNotificationForPatient(context: Context,to:String?, data: PatientVO): NotificationVO {
    val notificationVO = NotificationVO()
    val dataVO = DataVO()
    notificationVO.to = to.toString()
    dataVO.title = context.getString(R.string.noti_title)
    dataVO.body = "${data.name}${context.getString(R.string.noti_body_for_doctor)}"
    dataVO.id = data.id
    notificationVO.data = dataVO
    return notificationVO
}

fun prepareNotificationForDoctor(context:Context, to:String?, doctorName : String, doctorId : String):NotificationVO{
    val notificationVO = NotificationVO()
    val dataVO = DataVO()
    notificationVO.to = to.toString()
    dataVO.title = context.getString(R.string.noti_title)
    dataVO.body = "$doctorName မှ သင့်အား လက်ခံလိုက်ပါ ပြီ ယခုပင်ဆွေးနွေးမှုစတင်နိုင်ပါပြီ"
    dataVO.id = doctorId
    notificationVO.data = dataVO
    return notificationVO
}

fun prepareNotification(context:Context, to:String?, doctorname: String , doctorId: String, postponeDate : String):NotificationVO{
    val notificationVO = NotificationVO()
    val dataVO = DataVO()
    notificationVO.to = to.toString()
    dataVO.title = context.getString(R.string.noti_title)
    dataVO.body = "$doctorname မှ သင့်အား $postponeDate သို့ ရက်ချိန်းယူမှု အချိန်အား သတ်မှတ်ပေးလိုက်ပါသည်"
    dataVO.id = doctorId
    notificationVO.data = dataVO
    return notificationVO
}