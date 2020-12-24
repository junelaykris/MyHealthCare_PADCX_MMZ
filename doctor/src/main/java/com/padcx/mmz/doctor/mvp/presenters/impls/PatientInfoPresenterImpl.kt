package com.padcx.mmz.doctor.mvp.presenters.impls

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.doctor.mvp.presenters.PatientInfoPresenter
import com.padcx.mmz.doctor.utils.getCurrentDate
import com.padcx.mmz.doctor.views.PatientInfoView
import com.padcx.mmz.shared.data.models.DoctorModel
import com.padcx.mmz.shared.data.models.impls.DoctorModelImpl
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter

/**
 * Created by Myint Myint Zaw on 12/15/2020.
 */
class PatientInfoPresenterImpl : PatientInfoPresenter, AbstractBasePresenter<PatientInfoView>() {

    private val mDoctorModel: DoctorModel = DoctorModelImpl
    lateinit var mOwner: LifecycleOwner

    override fun onTapStartConsultation(consultationRequestVO: ConsultationRequestVO) {

        mDoctorModel.startConsultation(consultationRequestVO.id,
            getCurrentDate(), consultationRequestVO.case_summary,
            consultationRequestVO.patient, consultationRequestVO.doctor_info,
            onSuccess = {} , onFailure = {})

        mDoctorModel.getConsultationByConsultationRequestId(consultationRequestVO.id, onSuccess = {}, onError = {})

        mDoctorModel.getConsultationByConsultationRequestIdFromDB(consultationRequestVO.id)
            .observe(mOwner, Observer {
                it?.let{
                    mView?.startConsultationChat(it.id.toString())
                   /* mView?.startConsultationChat(it.consultation_id.toString())*/
                }

            })
    }

    override fun onUiReadyConsultation(consultationRequestId: String, owner: LifecycleOwner) {
        mOwner =owner

       // mDoctorModel.getConsultationByConsultationRequestId(consultationRequestId, onSuccess = {}, onError = {})

        mDoctorModel.getConsultationByConsultationRequestIdFromDB(consultationRequestId)
            .observe(owner, Observer {
                it?.let{
                    mView?.displayPatientInfo(it)
                }

            })
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }
}