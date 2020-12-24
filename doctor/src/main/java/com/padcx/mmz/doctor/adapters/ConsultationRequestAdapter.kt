package com.padcx.mmz.doctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.delegates.ConsultationRequestDelegate
import com.padcx.mmz.doctor.viewholders.ConsultationRequestViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.data.vos.ConsultedPatientVO

/**
 * Created by Myint Myint Zaw on 12/15/2020.
 */
class ConsultationRequestAdapter(private val mDelegate: ConsultationRequestDelegate) :
    BaseRecyclerAdapter<ConsultationRequestViewHolder, ConsultationRequestVO>() {

    var consultedPatientArrList : List<ConsultedPatientVO> = arrayListOf()

    fun setConsultedPatientList(list: List<ConsultedPatientVO>)
    {
        consultedPatientArrList =list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultationRequestViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.consultation_request_item, parent, false)
        return ConsultationRequestViewHolder(view,consultedPatientArrList, mDelegate)

    }
}