package com.padcx.mmz.doctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.delegates.ConsultationHistoryItemDelegate
import com.padcx.mmz.doctor.viewholders.ConsultationHistoryViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
class ConsultationHistoryAdapter (private val mDelegate: ConsultationHistoryItemDelegate) :
    BaseRecyclerAdapter<ConsultationHistoryViewHolder, ConsultationChatVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultationHistoryViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_consultation_item, parent, false)
        return ConsultationHistoryViewHolder(view, mDelegate)

    }
}