package com.padcx.mmz.doctor.views

import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.data.vos.ConsultedPatientVO
import com.padcx.mmz.shared.views.BaseView

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
interface HomeView : BaseView {
    fun displayConsultationRequests(list: List<ConsultationRequestVO>)
    fun displayConsultationAcceptHistoryList(list: List<ConsultationRequestVO>)

    fun displayConsultedPatient(list : List<ConsultedPatientVO>)
    fun displayPatientInfo(requestId : String)

    fun displayConsultationList(list: List<ConsultationChatVO>)

    fun displayPostPoneChooserDialog(consultationRequestVO: ConsultationRequestVO)
    fun displayPostponseProcessSuccess()

    fun displayPatientInfoDialog(consultationChatVO: ConsultationChatVO)
    fun displayPrescriptionDialog(consultation_chat_id: String, patient_name : String, start_conservation_date : String)
    fun nextPageChatRoom(consultation_chat_id : String)
    fun displayMedicalCommentDialog(consultationChatVO: ConsultationChatVO)

}