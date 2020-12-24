package com.padcx.mmz.doctor.mvp.views

import com.padcx.mmz.shared.views.BaseView

interface MedicalRecordView : BaseView {
    fun showSnackBar(message : String)
}