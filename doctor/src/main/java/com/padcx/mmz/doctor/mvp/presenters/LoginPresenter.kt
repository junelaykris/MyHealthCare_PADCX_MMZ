package com.padcx.mmz.doctor.mvp.presenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.doctor.mvp.views.LoginView
import com.padcx.mmz.doctor.mvp.views.RegisterView
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.presenter.BasePresenter

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapLogin(context: Context,email: String, password: String,owner: LifecycleOwner)
    fun onTapRegister()
}