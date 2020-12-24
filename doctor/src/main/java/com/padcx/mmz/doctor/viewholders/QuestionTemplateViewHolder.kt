package com.padcx.mmz.doctor.viewholders

import android.view.View
import com.padcx.mmz.doctor.delegates.QuestionTemplateDelegate
import com.padcx.mmz.shared.data.vos.GeneralQuestionTemplateVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.question_listitem_template.view.*

class QuestionTemplateViewHolder(itemView: View, private val mDelegate: QuestionTemplateDelegate) :
        BaseViewHolder<GeneralQuestionTemplateVO>(itemView) {

    override fun bindData(data: GeneralQuestionTemplateVO) {

        data?.let {
            itemView.tvQuestion.text = data.question
        }

        itemView.tvQuestion.setOnClickListener {
            mDelegate.onTapOneQuestion(data)
        }
    }
}