package com.padcx.mmz.myhealthcare.mvp.presenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.myhealthcare.mvp.views.LoginView
import com.padcx.mmz.myhealthcare.mvp.views.RegisterView
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.presenter.BasePresenter

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapLogin(context : Context, email: String, password: String,owner: LifecycleOwner)
    fun onTapRegister()
}