package com.padcx.mmz.myhealthcare.mvp.presenters

import android.content.Context
import com.padcx.mmz.myhealthcare.mvp.views.RegisterView
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.presenter.BasePresenter

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
interface RegisterPresenter : BasePresenter<RegisterView> {
    fun onTapRegister(context : Context,username: String, email: String, password: String,deviceId: String)
    fun navigateToLoginScreen()
}