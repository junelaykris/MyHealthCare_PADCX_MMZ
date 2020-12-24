package com.padcx.mmz.myhealthcare.mvp.presenters

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcx.mmz.myhealthcare.delegates.SpecialQuestionDelegate
import com.padcx.mmz.myhealthcare.mvp.views.CaseSummaryView
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO
import com.padcx.mmz.shared.presenter.BasePresenter

/**
 * Created by Myint Myint Zaw on 12/12/2020.
 */
interface CaseSummaryPresenter : BasePresenter<CaseSummaryView>, SpecialQuestionDelegate {
    fun onUiReadyForSpecialQuestion(context: Context, speciality: String, owner: LifecycleOwner)
    fun onUiReadyForGeneralQuestion(context: Context, email: String, owner: LifecycleOwner)
    fun onTapSendBroadCast(
        context: Context?,
        speciality: String?,
        questionAnswerList: List<QuestionAnswerVO>,
        patientVO: PatientVO,
        patientVO1: DoctorVO
    )
    fun navigateToNextScreen()
}