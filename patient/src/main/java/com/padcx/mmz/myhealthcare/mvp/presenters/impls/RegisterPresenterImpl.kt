package com.padcx.mmz.myhealthcare.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.myhealthcare.mvp.presenters.RegisterPresenter
import com.padcx.mmz.myhealthcare.mvp.views.RegisterView
import com.padcx.mmz.shared.data.models.AuthenticationModel
import com.padcx.mmz.shared.data.models.PatientModel
import com.padcx.mmz.shared.data.models.impls.AuthenticationModelImpl
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter
import java.util.*

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
class RegisterPresenterImpl : RegisterPresenter, AbstractBasePresenter<RegisterView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl
    /*private val myCareModel : MyHealthCareModel = MyHealthCareModelImpl*/

    private val mPatientModel: PatientModel = PatientModelImpl
    override fun onTapRegister(
        context: Context,
        username: String,
        email: String,
        password: String,
        deviceId: String
    ) {
        if (email.isNullOrEmpty() || password.isNullOrEmpty() || username.isNullOrEmpty()) {
            mView?.showError("Please enter required fields")
        } else {

            val patientVO = PatientVO(
                id = UUID.randomUUID().toString(),
                name = username,
                email = email,
                deviceId = deviceId
            )

            mAuthenticationModel.register(username, email, password, deviceId, onSuccess = {
                mView?.navigateToToLoginScreen()
                mPatientModel.saveNewPatientRegistration(patientVO, onSuccess = {
                    //  mPatientModel.savePatientToDb(patientVO)
                }, onError = {})

            }, onFailure = {
                mView?.showError(it)
            })

        }
    }

    override fun navigateToLoginScreen() {
        mView?.navigateToToLoginScreen()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }


}