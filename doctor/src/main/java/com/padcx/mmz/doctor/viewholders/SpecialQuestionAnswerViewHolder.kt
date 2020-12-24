package com.padcx.mmz.doctor.viewholders

import android.view.View
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.special_quesionanswer_item.view.*

/**
 * Created by Myint Myint Zaw on 12/15/2020.
 */
class SpecialQuestionAnswerViewHolder (itemView: View) :
    BaseViewHolder<QuestionAnswerVO>(itemView) {

    override fun bindData(data: QuestionAnswerVO) {

        data?.let {
            itemView.txtQuestion.text = "(${adapterPosition}) "+ data.question
            itemView.txtAnswer.text =data.answer
        }

    }
}