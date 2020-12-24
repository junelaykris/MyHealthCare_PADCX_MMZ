package com.padcx.mmz.doctor.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.doctor.mvp.presenters.PrescriptionInfoPresenter
import com.padcx.mmz.doctor.mvp.views.PrescriptionInfoView
import com.padcx.mmz.shared.data.models.DoctorModel
import com.padcx.mmz.shared.data.models.impls.DoctorModelImpl
import com.padcx.mmz.shared.presenter.AbstractBasePresenter

class PrescriptionInfoPresenterImpl : PrescriptionInfoPresenter, AbstractBasePresenter<PrescriptionInfoView>() {

    private val doctorModel: DoctorModel = DoctorModelImpl
    lateinit var mOwner: LifecycleOwner
    override fun onUiReadyForPrescription(consulation_chat_id: String) {

        doctorModel.getPrescription(consulation_chat_id, onSuccess = {}, onError = {})

        doctorModel.getPrescriptionFromDB(consulation_chat_id)
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