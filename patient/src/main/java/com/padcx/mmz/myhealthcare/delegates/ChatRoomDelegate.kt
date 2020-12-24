package com.padcx.mmz.myhealthcare.delegates

/**
 * Created by Myint Myint Zaw on 12/16/2020.
 */
interface ChatRoomDelegate {
    fun onTapSendTextMessage(message : String )
    fun onTapAttachImage()
    fun onTapQuestionTemplate()
    fun onTapPrescription()
    fun onTapDoctorComment()
}