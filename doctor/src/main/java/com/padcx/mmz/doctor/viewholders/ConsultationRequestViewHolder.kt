package com.padcx.mmz.doctor.viewholders

import android.view.View
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.delegates.ConsultationRequestDelegate
import com.padcx.mmz.doctor.utils.showCropImage
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.data.vos.ConsultedPatientVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.consultation_request_item.view.*

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
class ConsultationRequestViewHolder(
    itemView: View,
    private val consultedPatientArrList: List<ConsultedPatientVO>,
    private val mDelegate: ConsultationRequestDelegate
) : BaseViewHolder<ConsultationRequestVO>(itemView) {

    override fun bindData(data: ConsultationRequestVO) {

        data?.let {
            itemView.ivPatient.showCropImage(it.patient?.photo)
            itemView.txtPatientName.text = data.patient?.name
            itemView.txtPatientDob.text = data.patient?.dob


            var data = consultedPatientArrList.filter {
                it.patient_id == data.patient.id
            }


            if(data.isEmpty())
            {
                itemView.txtPatientType.text =  itemView.resources.getString(R.string.new_patient)
                itemView.btnNext.visibility =View.GONE
                itemView.btnPostpone.visibility = View.GONE
                itemView.btnSkip.visibility = View.VISIBLE
            }
            else
            {
                itemView.txtPatientType.text =  itemView.resources.getString(R.string.consulted_patient)
                itemView.btnNext.visibility =View.VISIBLE
                itemView.btnPostpone.visibility = View.VISIBLE
                itemView.btnSkip.visibility = View.GONE
            }
        }

        itemView.btnAccept.setOnClickListener {
            mDelegate.onTapAccept(data)
        }

        itemView.btnNext.setOnClickListener {
            mDelegate.onTapNext(data)
        }

        itemView.btnPostpone.setOnClickListener {
            mDelegate.onTapPostpone(data)
        }

        itemView.btnSkip.setOnClickListener {
            mDelegate.onTapSkip(data)
        }
    }
}