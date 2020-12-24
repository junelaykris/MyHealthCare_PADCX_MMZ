package com.padcx.mmz.myhealthcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.delegates.SpecialityItemDelegate
import com.padcx.mmz.myhealthcare.viewholders.SpecialityViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.SpecialitiesVO

/**
 * Created by Myint Myint Zaw on 12/10/2020.
 */
class SpecialityAdapter (private val mDelegate: SpecialityItemDelegate) :
    BaseRecyclerAdapter<SpecialityViewHolder, SpecialitiesVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialityViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.speciality_list_item, parent, false)
        return SpecialityViewHolder(view, mDelegate)

    }
}
