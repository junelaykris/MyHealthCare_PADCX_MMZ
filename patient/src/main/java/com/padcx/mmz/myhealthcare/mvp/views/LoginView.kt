package com.padcx.mmz.myhealthcare.mvp.views

import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.views.BaseView

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
interface LoginView: BaseView {
    fun navigateToHomeScreen(patientVO: PatientVO)
    fun navigateToRegisterScreen()
}