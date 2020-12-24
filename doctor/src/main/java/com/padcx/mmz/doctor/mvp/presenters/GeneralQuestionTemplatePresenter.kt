package com.padcx.mmz.doctor.mvp.presenters

import com.padcx.mmz.doctor.delegates.QuestionTemplateDelegate
import com.padcx.mmz.doctor.mvp.views.GeneralQuestionTemplateView
import com.padcx.mmz.shared.presenter.BasePresenter


interface GeneralQuestionTemplatePresenter : BasePresenter<GeneralQuestionTemplateView>,
    QuestionTemplateDelegate {
}