package com.padcx.mmz.doctor.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.doctor.views.PatientInfoView
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.presenter.BasePresenter

/**
 * Created by Myint Myint Zaw on 12/15/2020.
 */
interface PatientInfoPresenter : BasePresenter<PatientInfoView> {
    fun onTapStartConsultation(consultationRequestVO: ConsultationRequestVO)
    fun onUiReadyConsultation( consultationRequestId: String ,  owner: LifecycleOwner)
}