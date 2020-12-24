package com.padcx.mmz.doctor.mvp.views

import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.views.BaseView

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
interface LoginView  : BaseView {
    fun navigateToHomeScreen(doctorVO: DoctorVO)
    fun navigateToRegisterScreen()

}