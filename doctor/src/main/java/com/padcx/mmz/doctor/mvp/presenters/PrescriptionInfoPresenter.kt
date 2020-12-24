package com.padcx.mmz.doctor.mvp.presenters

import com.padcx.mmz.doctor.mvp.views.PrescriptionInfoView
import com.padcx.mmz.shared.presenter.BasePresenter

interface PrescriptionInfoPresenter : BasePresenter<PrescriptionInfoView>{
    fun onUiReadyForPrescription ( consulation_chat_id : String)
}