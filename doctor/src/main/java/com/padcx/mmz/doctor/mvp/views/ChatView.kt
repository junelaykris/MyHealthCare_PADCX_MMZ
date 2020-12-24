package com.padcx.mmz.doctor.mvp.views

import com.padcx.mmz.shared.data.vos.ChatMessageVO
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.views.BaseView

/**
 * Created by Myint Myint Zaw on 12/16/2020.
 */
interface ChatView : BaseView {
    fun displayPatientInfo(consultationChatVO: ConsultationChatVO)
    fun displayChatMessageList(list : List<ChatMessageVO>)
    fun displayPrescriptionViewPod(prescription_list : List<PrescriptionVO>)
}