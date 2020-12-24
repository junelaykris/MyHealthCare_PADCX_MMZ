package com.padcx.mmz.myhealthcare.mvp.views

import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.views.BaseView


interface PrescriptionInfoView : BaseView {
    fun displayPrescriptionList(prescription_list : List<PrescriptionVO>)
}