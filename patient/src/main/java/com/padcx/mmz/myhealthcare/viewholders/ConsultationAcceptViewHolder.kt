package com.padcx.mmz.myhealthcare.viewholders

import android.view.View
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.delegates.ConsultationAcceptDelegate
import com.padcx.mmz.myhealthcare.utils.showCropImage
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.startconsultation_request_view.view.*

class ConsultationAcceptViewHolder(itemView: View, private val mDelegate: ConsultationAcceptDelegate) :
        BaseViewHolder<ConsultationRequestVO>(itemView) {


    override fun bindData(data: ConsultationRequestVO) {

        data?.let {
        itemView.txtConsultation.text = data.doctor_info.speciality + itemView.resources.getString(R.string.consultation_request_message)
        itemView.ivUserProfile.showCropImage(data.doctor_info.photo)
        itemView.txtDoctorName.text = data.doctor_info?.name
        itemView.txtSpecialityName.text = data.doctor_info?.speciality
        itemView.txtDoctorProfile.text = data.doctor_info?.biography
        }

        itemView.btnStartConsultation.setOnClickListener {
            mDelegate.onTapStarted(consultationChatId = data.consultation_id.toString(),data)
        }

    }
}