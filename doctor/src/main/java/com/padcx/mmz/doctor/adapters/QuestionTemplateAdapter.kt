package com.padcx.mmz.doctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.delegates.QuestionTemplateDelegate
import com.padcx.mmz.doctor.viewholders.QuestionTemplateViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.GeneralQuestionTemplateVO

class QuestionTemplateAdapter(private val mDelegate: QuestionTemplateDelegate) :
        BaseRecyclerAdapter<QuestionTemplateViewHolder, GeneralQuestionTemplateVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionTemplateViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_listitem_template, parent, false)
        return QuestionTemplateViewHolder(view, mDelegate)

    }
}
