package com.padcx.mmz.myhealthcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.viewholders.SpecialQuestionAnswerViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO

/**
 * Created by Myint Myint Zaw on 12/13/2020.
 */
class SpecialQuestionAnswerAdapter(var type : String) :
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
