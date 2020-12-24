package com.padcx.mmz.doctor.viewholders

import android.view.View
import com.padcx.mmz.doctor.delegates.ConsultationHistoryItemDelegate
import com.padcx.mmz.doctor.utils.showCropImage
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.history_consultation_item.view.*

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
class ConsultationHistoryViewHolder(itemView: View, private val mDelegate: ConsultationHistoryItemDelegate) :
    BaseViewHolder<ConsultationChatVO>(itemView) {

    override fun bindData(data: ConsultationChatVO) {
        data?.let {

            itemView.ivProfile.showCropImage(data.patient?.photo)
            itemView.tvName.text = data.patient?.name
            itemView.tvDate.text = data.patient?.dob
        }

        itemView.txtTreatmentHistory.setOnClickListener {
            mDelegate.onTapMedicalRecord(data)
        }

        itemView.txtPrescription.setOnClickListener {
            mDelegate.onTapPrescription(data)
        }

        itemView.txtRemark.setOnClickListener {
            mDelegate.onTapRemark(data)
        }
    }
}