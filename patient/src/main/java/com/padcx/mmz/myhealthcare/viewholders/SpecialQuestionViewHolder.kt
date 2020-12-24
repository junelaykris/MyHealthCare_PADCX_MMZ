package com.padcx.mmz.myhealthcare.viewholders

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.padcx.mmz.myhealthcare.delegates.SpecialQuestionDelegate
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO
import com.padcx.mmz.shared.data.vos.SpecialQuestionVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.special_question_listitem.view.*

/**
 * Created by Myint Myint Zaw on 12/13/2020.
 */
class SpecialQuestionViewHolder(itemView: View, var mQuestionAnswerList: List<QuestionAnswerVO>, private val mDelegate: SpecialQuestionDelegate
) : BaseViewHolder<SpecialQuestionVO>(itemView) {

    override fun bindData(data: SpecialQuestionVO) {

        data?.let {
            itemView.txtSpecialQuestion.text = "(${adapterPosition+1}) ${data.question}"
        }

        mQuestionAnswerList?.let {
            itemView.edtAnswer.text = Editable.Factory.getInstance().newEditable(mQuestionAnswerList[adapterPosition].answer)
        }

        itemView.edtAnswer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                var questionAnswerVO= QuestionAnswerVO(data.id,data?.question,itemView.edtAnswer.text.toString())
                mDelegate.onAnswerChange(adapterPosition,questionAnswerVO)
            }

            override fun afterTextChanged(editable: Editable) {}
        })

    }
}