package com.padcx.mmz.myhealthcare.mvp.presenters.impls


import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.myhealthcare.mvp.presenters.PrescriptionInfoPresenter
import com.padcx.mmz.myhealthcare.mvp.views.PrescriptionInfoView
import com.padcx.mmz.shared.data.models.PatientModel
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.presenter.AbstractBasePresenter

class PrescriptionInfoPresenterImpl : PrescriptionInfoPresenter, AbstractBasePresenter<PrescriptionInfoView>() {

    private val mModel: PatientModel = PatientModelImpl
    lateinit var mOwner: LifecycleOwner

    override fun onUiReadyForPrescription(consulation_chat_id: String) {

        mModel.getPrescription(consulation_chat_id, onSuccess = {}, onError = {})

        mModel.getPrescriptionFromDB()
                .observe(mOwner, Observer {
                    it?.let{
                        mView?.displayPrescriptionList(it)
                    }
                })

    }


    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mOwner = owner
    }

}