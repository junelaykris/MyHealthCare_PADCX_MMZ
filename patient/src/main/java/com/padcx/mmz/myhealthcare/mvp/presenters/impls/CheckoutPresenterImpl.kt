package com.padcx.mmz.myhealthcare.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.myhealthcare.mvp.presenters.CheckoutPresenter
import com.padcx.mmz.myhealthcare.mvp.views.CheckOutView
import com.padcx.mmz.myhealthcare.utils.MyUserManager
import com.padcx.mmz.shared.data.models.PatientModel
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter


class CheckoutPresenterImpl : CheckoutPresenter, AbstractBasePresenter<CheckOutView>() {

    private val patientModel: PatientModel = PatientModelImpl
    private lateinit var mOwner: LifecycleOwner


    override fun onUiReadyCheckout(consultationChatId: String, owner: LifecycleOwner) {

        patientModel.getPrescription(consultationChatId, onSuccess = {}, onError = {})

        patientModel.getPrescriptionFromDB()
            .observe(owner, Observer {
                it?.let {
                    mView?.displayPrescription(it)
                }
            })

        patientModel.getPatientByEmail(
            MyUserManager.getUser().email.toString(),
            onSuccess = {},
            onError = {})

        patientModel.getPatientByEmailFromDB(MyUserManager.getUser().email.toString())
            .observe(owner, Observer {
                it?.let {
                    MyUserManager.saveUser(it)
                    //SessionManager.addPatientInfo(it)
                    mView?.displayShippingAddress(it.address)
                }
            })
    }

    override fun onTapCheckout(
        prescriotionList: List<PrescriptionVO>,
        deliveryAddressVO: String,
        doctorVO: DoctorVO?,
        patientVO: PatientVO?,
        total_price: String
    ) {
        if (doctorVO != null && patientVO != null) {
            patientModel.checkout(prescriotionList,
                deliveryAddressVO,
                doctorVO,
                patientVO,
                total_price,
                onSuccess = {}, onFailure = {})
            mView?.displayConfirmDialog(prescriotionList, deliveryAddressVO, total_price)
        }
    }

    override fun onTapAddShipping(patientVO: PatientVO?) {

        patientVO?.let {
            patientModel.addedPatientInfo(it, onSuccess = {}, onError = {})
        }

        patientModel.getPatientByEmail(
            MyUserManager.getUser().email.toString(),
            onSuccess = {},
            onError = {})

        patientModel.getPatientByEmailFromDB(MyUserManager.getUser().email.toString())
            .observe(mOwner, Observer {
                it?.let {
                    MyUserManager.saveUser(it)
                    // SessionManager.addPatientInfo(it)
                    mView?.displayShippingAddress(it.address)
                }
            })
    }


    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mOwner = owner
    }

    override fun onTapSelected(address: String, previousPositon: Int) {
        mView?.selectedShippingAddress(address, previousPositon)
    }

}