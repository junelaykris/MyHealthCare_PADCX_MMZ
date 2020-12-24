package com.padcx.mmz.doctor.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.doctor.mvp.presenters.LoginPresenter
import com.padcx.mmz.doctor.mvp.views.LoginView
import com.padcx.mmz.shared.data.models.AuthenticationModel
import com.padcx.mmz.shared.data.models.DoctorModel
import com.padcx.mmz.shared.data.models.impls.AuthenticationModelImpl
import com.padcx.mmz.shared.data.models.impls.DoctorModelImpl
import com.padcx.mmz.shared.presenter.AbstractBasePresenter

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
class LoginPresenterImpl : LoginPresenter, AbstractBasePresenter<LoginView>() {
    /*    private val myCareModel: MyHealthCareModel = MyHealthCareModelImpl*/
    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl
    private val mDoctorModel: DoctorModel = DoctorModelImpl

    override fun onTapLogin(
        context: Context,
        email: String,
        password: String,
        owner: LifecycleOwner
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            mView?.showError("Please enter all the fields")
        } else {
            mAuthenticationModel.login(email, password, onSuccess = {
                mDoctorModel.getDoctorByEmail(email,onSuccess = {}, onError = {})
                mDoctorModel.getDoctorByEmailFromDB(email)
                    .observe(owner, Observer { doctor ->
                        doctor?.let {
                            mView?.navigateToHomeScreen(doctor) }
                    })
            }, onFailure = {
                mView?.showError(it)
            })
        }
    }

    override fun onTapRegister() {
        mView?.navigateToRegisterScreen()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
    }


}