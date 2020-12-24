package com.padcx.mmz.doctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.viewholders.PrescriptionInfoViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.PrescriptionVO

class PrescriptionInfoAdapter() :
        BaseRecyclerAdapter<PrescriptionInfoViewHolder, PrescriptionVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionInfoViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_prescription_info, parent, false)
        return PrescriptionInfoViewHolder(view)

    }
}
