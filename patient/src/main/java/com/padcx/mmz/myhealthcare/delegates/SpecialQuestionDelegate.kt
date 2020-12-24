package com.padcx.mmz.myhealthcare.delegates

import com.padcx.mmz.shared.data.vos.QuestionAnswerVO

/**
 * Created by Myint Myint Zaw on 12/13/2020.
 */
interface SpecialQuestionDelegate {
    fun onAnswerChange(position: Int, questionAnswerVO: QuestionAnswerVO)
}