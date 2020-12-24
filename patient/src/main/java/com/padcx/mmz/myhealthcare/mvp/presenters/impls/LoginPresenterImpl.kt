package com.padcx.mmz.myhealthcare.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.myhealthcare.mvp.presenters.LoginPresenter
import com.padcx.mmz.myhealthcare.mvp.views.LoginView
import com.padcx.mmz.shared.data.models.AuthenticationModel
import com.padcx.mmz.shared.data.models.PatientModel
import com.padcx.mmz.shared.data.models.impls.AuthenticationModelImpl
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.presenter.AbstractBasePresenter
import androidx.lifecycle.Observer

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class LoginPresenterImpl: LoginPresenter, AbstractBasePresenter<LoginView>(){

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl

    private val mPatientModel: PatientModel = PatientModelImpl

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
                mPatientModel.getPatientByEmail(email,onSuccess = {}, onError = {})
                mPatientModel.getPatientByEmailFromDB(email)
                    .observe(owner, Observer { patient ->
                        patient?.let {
                            mView?.navigateToHomeScreen(patient) }
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
