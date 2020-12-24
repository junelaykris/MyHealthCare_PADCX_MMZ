package com.padcx.mmz.myhealthcare.mvp.presenters

import com.padcx.mmz.myhealthcare.mvp.views.PrescriptionInfoView
import com.padcx.mmz.shared.presenter.BasePresenter

interface PrescriptionInfoPresenter : BasePresenter<PrescriptionInfoView> {
    fun onUiReadyForPrescription(chatID: String)
}