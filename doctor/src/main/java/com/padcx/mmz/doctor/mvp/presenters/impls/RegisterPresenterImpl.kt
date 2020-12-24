package com.padcx.mmz.doctor.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.doctor.mvp.presenters.RegisterPresenter
import com.padcx.mmz.doctor.mvp.views.RegisterView
import com.padcx.mmz.shared.data.models.AuthenticationModel
import com.padcx.mmz.shared.data.models.DoctorModel
import com.padcx.mmz.shared.data.models.impls.AuthenticationModelImpl
import com.padcx.mmz.shared.data.models.impls.DoctorModelImpl
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter
import java.util.*

/**
 * Created by Myint Myint Zaw on 11/28/2020.
 */
class RegisterPresenterImpl : RegisterPresenter, AbstractBasePresenter<RegisterView>() {

    private val mAuthenticationModel: AuthenticationModel = AuthenticationModelImpl
/*    private val myCareModel: MyHealthCareModel = MyHealthCareModelImpl*/
    private val mDoctorModel: DoctorModel = DoctorModelImpl

  /*  override fun onTapRegister(context: Context, doctorVO: DoctorVO, password: String) {
        if (doctorVO.email.isNullOrEmpty() || password.isNullOrEmpty() || doctorVO.name!!.isNullOrEmpty()) {
            mView?.showError("Please enter required fields")
        } else {
          *//*  mAuthenticationModel.register(doctorVO.name.toString(), doctorVO.email.toString(), password, onSuccess = {
                mDoctorModel.saveNewDoctorRegistration(doctorVO, onSuccess = {
                    mDoctorModel?.saveDoctorToDb(doctorVO)
                    mView?.navigateToLoginScreen()
                }, onError = {})

            }, onFailure = {
                mView?.showError(it)
            })
*//*
        }
    }*/

    override fun onTapRegister(
        context: Context,
        username: String,
        email: String,
        password: String,
        deviceId: String,
        specialityName: String,
        speciality_type: String,
        phone: String,
        degree: String,
        biography: String
    ) {
        if(email.isEmpty() || password.isEmpty() || username.isEmpty() || specialityName.isEmpty() || speciality_type.isEmpty() ||
            phone.isEmpty() || degree.isEmpty() || biography.isEmpty()){
            mView?.showError("Please enter required fields")
        } else {

            val doctorVO = DoctorVO(
                id = UUID.randomUUID().toString(),
                name = username,
                email = email,
                deviceId = deviceId,
                biography = biography,
                degree = degree,
                phone = phone,
                speciality = specialityName
            )

            mAuthenticationModel.register(username,  email, password,deviceId, onSuccess = {
                mView?.navigateToLoginScreen()
                mDoctorModel.saveNewDoctorRegistration(doctorVO = doctorVO,  onSuccess = {},onError = {} )
            }, onFailure = {
                mView?.showError(it)
            })

        }
    }

    override fun navigateToLoginScreen() {
        mView?.navigateToLoginScreen()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
    }
}