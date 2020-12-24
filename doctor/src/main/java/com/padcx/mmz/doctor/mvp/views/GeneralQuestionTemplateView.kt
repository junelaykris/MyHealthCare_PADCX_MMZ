package com.padcx.mmz.doctor.mvp.views

import com.padcx.mmz.shared.data.vos.GeneralQuestionTemplateVO
import com.padcx.mmz.shared.views.BaseView

interface GeneralQuestionTemplateView : BaseView {
    fun displayGeneralQuestions(list : List<GeneralQuestionTemplateVO>)
    fun navigateToToChatRoom(questions : String)
}