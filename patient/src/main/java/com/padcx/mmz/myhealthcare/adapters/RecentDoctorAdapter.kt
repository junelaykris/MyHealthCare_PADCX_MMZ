package com.padcx.mmz.myhealthcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.delegates.RecentDoctorViewItemActionDelegate
import com.padcx.mmz.myhealthcare.viewholders.RecentDoctorViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.RecentDoctorVO

class RecentDoctorAdapter(private val mDelegate: RecentDoctorViewItemActionDelegate) :
    BaseRecyclerAdapter<RecentDoctorViewHolder, RecentDoctorVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentDoctorViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_recent_doctor, parent, false)
        return RecentDoctorViewHolder(view, mDelegate)

    }
}
