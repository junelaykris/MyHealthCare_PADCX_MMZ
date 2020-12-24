package com.padcx.mmz.doctor.views

import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.views.BaseView

/**
 * Created by Myint Myint Zaw on 12/15/2020.
 */
interface PatientInfoView  : BaseView {
    fun displayPatientInfo(consultationRequestVO: ConsultationRequestVO)
    fun startConsultationChat(chatId: String)
}