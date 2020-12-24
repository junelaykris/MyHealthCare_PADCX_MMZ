package com.padcx.mmz.myhealthcare.viewholders

import android.view.View
import com.padcx.mmz.myhealthcare.delegates.ChatHistoryDelegate
import com.padcx.mmz.myhealthcare.utils.showCropImage
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.listitem_chathistory.view.*


class ChatHistoryViewHolder(itemView: View, private val mDelegate: ChatHistoryDelegate) :
    BaseViewHolder<ConsultationChatVO>(itemView) {


    override fun bindData(data: ConsultationChatVO) {

        data?.let {
            itemView.tvDate.text = data.start_consultation_date
            itemView.ivDoctorPhoto.showCropImage(data.doctor_info?.photo.toString())
            itemView.tvDoctorName.text = data.doctor_info?.name
            itemView.tvSpecialityName.text = data.doctor_info?.speciality
        }

        itemView.sendtextlayout.setOnClickListener {
            mDelegate.onTapSendMessage(data)
        }

        itemView.prescriptionlayout.setOnClickListener {
            mDelegate.onTapPrescription(data)
        }


    }
}