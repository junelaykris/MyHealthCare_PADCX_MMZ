package com.padcx.mmz.myhealthcare.mvp.presenters.impls

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.padcx.mmz.myhealthcare.mvp.presenters.CaseSummaryPresenter
import com.padcx.mmz.myhealthcare.mvp.views.CaseSummaryView
import com.padcx.mmz.myhealthcare.utils.getCurrentDate
import com.padcx.mmz.shared.data.models.PatientModel
import com.padcx.mmz.shared.data.models.impls.PatientModelImpl
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO
import com.padcx.mmz.shared.network.responses.NotificationVO
import com.padcx.mmz.shared.presenter.AbstractBasePresenter
import com.padcx.mmz.shared.utils.prepareNotificationForPatient

/**
 * Created by Myint Myint Zaw on 12/13/2020.
 */
class CaseSummaryPresenterImpl: CaseSummaryPresenter, AbstractBasePresenter<CaseSummaryView>() {

    private val mPatientModel: PatientModel = PatientModelImpl

    override fun onUiReadyForSpecialQuestion(
        context: Context,
        speciality: String,
        owner: LifecycleOwner
    ) {
        mPatientModel.getSpecialQuestionBySpeciality(speciality, onSuccess = {} , onFailure = {})

        mPatientModel.getSpecialQuestionBySpecialityFromDb("")
            .observe(owner, Observer {
                mView?.displaySpecialQuestions(it)
            })
    }

    override fun onUiReadyForGeneralQuestion(
        context: Context,
        email: String,
        owner: LifecycleOwner
    ) {
        mPatientModel.getPatientByEmailFromDB(email)
            .observe(owner, Observer { patient ->
                if(patient.blood_type.isNullOrBlank())
                {
                    mView?.displayOnceGeneralQuestion()
                    mView?.displayPatientData(patient)
                } else
                {
                    mView?.displayAlwaysGeneralQuestion()
                    mView?.displayPatientData(patient)
                }
            })
    }

    override fun onTapSendBroadCast(
        context: Context?,
        speciality: String?,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        doctorVO: DoctorVO
    ) {
        speciality?.let{
            mPatientModel.sendBroadCastConsultationRequest(speciality,questionAnswerList,patientVO,doctorVO,
                getCurrentDate(),
                onSuccess = {} , onFailure = {})

/*
            val notiData : NotificationVO
            if(doctorVO.id.isEmpty()) {
                notiData = prepareNotificationForPatient(context!!, "/topics/$speciality", patientVO)
            }else
            {
                notiData = prepareNotificationForPatient(context!!, doctorVO.deviceId, patientVO)
            }
            mPatientModel.sendBroadcastToDoctor(notiData, onSuccess = {
                Log.d("onsuccess", it.success.toString())
            }, onFailure = {
                Log.d("Failure", it)
            })*/
        }
    }


    override fun navigateToNextScreen() {

    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }

    override fun onAnswerChange(position: Int, questionAnswerVO: QuestionAnswerVO) {
        mView?.replaceQuestionAnswerList(position,questionAnswerVO)
    }


}