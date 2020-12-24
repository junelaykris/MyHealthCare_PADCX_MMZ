package com.padcx.mmz.doctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.viewholders.SpecialQuestionAnswerViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO

/**
 * Created by Myint Myint Zaw on 12/15/2020.
 */
class SpecialQuestionAdapter (var type : String) :
    BaseRecyclerAdapter<SpecialQuestionAnswerViewHolder, QuestionAnswerVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialQuestionAnswerViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.special_quesionanswer_item, parent, false)
        return SpecialQuestionAnswerViewHolder(view)

    }

    override fun getItemCount(): Int {
        if (type == "chat") {
            if (mData.size > 0) {
                return 2
            } else  return super.getItemCount()
        } else {
            return super.getItemCount()
        }
    }
}
