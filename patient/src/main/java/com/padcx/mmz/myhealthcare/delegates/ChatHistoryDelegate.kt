package com.padcx.mmz.myhealthcare.delegates

import com.padcx.mmz.shared.data.vos.ConsultationChatVO


interface ChatHistoryDelegate {
    fun onTapSendMessage(consultationChatVO: ConsultationChatVO)
    fun onTapPrescription(consultationChatVO: ConsultationChatVO)
}