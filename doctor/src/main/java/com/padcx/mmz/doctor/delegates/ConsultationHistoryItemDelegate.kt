package com.padcx.mmz.doctor.delegates

import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
interface ConsultationHistoryItemDelegate {
    fun onTapMedicalRecord(data: ConsultationChatVO)
    fun onTapPrescription(data: ConsultationChatVO)
    fun onTapSendMessage(data: ConsultationChatVO)
    fun onTapRemark(data: ConsultationChatVO)
}