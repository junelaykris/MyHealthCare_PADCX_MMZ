package com.padcx.mmz.doctor.delegates

import com.padcx.mmz.shared.data.vos.GeneralQuestionTemplateVO


interface QuestionTemplateDelegate {
    fun onTapOneQuestion(generalQuestionTemplateVO: GeneralQuestionTemplateVO)
}