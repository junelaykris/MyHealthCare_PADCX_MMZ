package com.padcx.mmz.myhealthcare.delegates

import com.padcx.mmz.shared.data.vos.ConsultationRequestVO

interface ConsultationAcceptDelegate {
    fun onTapStarted(consultationChatId : String, consultationRequestVO: ConsultationRequestVO)
}