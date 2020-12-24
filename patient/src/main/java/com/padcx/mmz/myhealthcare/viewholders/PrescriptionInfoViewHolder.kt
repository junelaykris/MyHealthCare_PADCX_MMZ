package com.padcx.mmz.myhealthcare.viewholders


import android.view.View
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.listitem_prescription_info.view.*

class PrescriptionInfoViewHolder(itemView: View) :
        BaseViewHolder<PrescriptionVO>(itemView) {

    override fun bindData(data: PrescriptionVO) {

        data?.let {
            itemView.medicine_name.text = data.medicine_name
            itemView.txt_amount.text  = data.routineVO?.amount +" mg"
            itemView.txt_quality.text = data.routineVO?.quantity + " Tablet"
            itemView.txt_time.text = data.routineVO?.days
            var times = data.routineVO?.times.toString()
            itemView.txt_count.text = times
            itemView.txt_repeat.text  = data.routineVO?.repeat
            itemView.txt_comment.text = data.routineVO?.comment
        }

    }
}