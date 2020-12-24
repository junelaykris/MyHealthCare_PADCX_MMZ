package com.padcx.mmz.myhealthcare.mvp.views

import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO
import com.padcx.mmz.shared.data.vos.SpecialQuestionVO
import com.padcx.mmz.shared.views.BaseView

/**
 * Created by Myint Myint Zaw on 12/12/2020.
 */
interface CaseSummaryView : BaseView {
    fun displaySpecialQuestions(list: List<SpecialQuestionVO>)
    fun displayOnceGeneralQuestion()
    fun displayAlwaysGeneralQuestion()
    fun replaceQuestionAnswerList(position : Int , questionAnswerVO: QuestionAnswerVO)
    fun displayPatientData(patientVO: PatientVO)

}