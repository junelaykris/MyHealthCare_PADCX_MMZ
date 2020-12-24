package com.padcx.mmz.doctor.mvp.presenters

import android.content.Context
import com.padcx.mmz.doctor.mvp.views.RegisterView
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.presenter.BasePresenter

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
interface RegisterPresenter : BasePresenter<RegisterView> {
    fun onTapRegister(context : Context,username: String, email: String, password: String,deviceId: String,
    specialityName:String,speciality_type: String, phone: String, degree: String, biography : String)
    //fun onTapRegister(context: Context, doctorVO: DoctorVO, password: String)
    fun navigateToLoginScreen()
}