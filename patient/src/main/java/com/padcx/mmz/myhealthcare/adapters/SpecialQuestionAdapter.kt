package com.padcx.mmz.myhealthcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.delegates.SpecialQuestionDelegate
import com.padcx.mmz.myhealthcare.viewholders.SpecialQuestionViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO
import com.padcx.mmz.shared.data.vos.SpecialQuestionVO

/**
 * Created by Myint Myint Zaw on 12/13/2020.
 */
class SpecialQuestionAdapter (private val mDelegate: SpecialQuestionDelegate
) : BaseRecyclerAdapter<SpecialQuestionViewHolder, SpecialQuestionVO>() {


    var mQuestionAnswerList: List<QuestionAnswerVO> = arrayListOf()

    fun setQuestionAnswerList( questionAnswerList: List<QuestionAnswerVO>)
    {
        mQuestionAnswerList =questionAnswerList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialQuestionViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.special_question_listitem, parent, false)
        return SpecialQuestionViewHolder(view,mQuestionAnswerList, mDelegate)
    }

    override fun onBindViewHolder(holder: SpecialQuestionViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }

}