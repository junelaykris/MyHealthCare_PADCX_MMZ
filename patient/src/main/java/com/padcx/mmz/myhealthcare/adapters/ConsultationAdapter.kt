package com.padcx.mmz.myhealthcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.delegates.ConsultationAcceptDelegate
import com.padcx.mmz.myhealthcare.viewholders.ConsultationAcceptViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO


class ConsultationAdapter(private val mDelegate: ConsultationAcceptDelegate) :
        BaseRecyclerAdapter<ConsultationAcceptViewHolder, ConsultationRequestVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultationAcceptViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.startconsultation_request_view, parent, false)
        return ConsultationAcceptViewHolder(view, mDelegate)

    }
}
