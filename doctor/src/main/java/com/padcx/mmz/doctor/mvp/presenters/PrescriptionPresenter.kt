package com.padcx.mmz.doctor.mvp.presenters

import com.padcx.mmz.doctor.delegates.MedicineDelegate
import com.padcx.mmz.doctor.mvp.views.PrescriptionView
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.presenter.BasePresenter

interface PrescriptionPresenter : BasePresenter<PrescriptionView>, MedicineDelegate {
    fun onUiReadyForPrescription ( speciality : String)
    fun onTapFinishConsultation(list : List<PrescriptionVO>, consultationChatVO: ConsultationChatVO)
}