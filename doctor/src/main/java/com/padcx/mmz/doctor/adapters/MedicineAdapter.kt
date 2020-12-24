package com.padcx.mmz.doctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.delegates.MedicineDelegate
import com.padcx.mmz.doctor.viewholders.MedicineViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.MedicineVO

/**
 * Created by Myint Myint Zaw on 12/18/2020.
 */
class MedicineAdapter(private val mDelegate: MedicineDelegate) :
    BaseRecyclerAdapter<MedicineViewHolder, MedicineVO>() {

    fun setMedicineList(list : ArrayList<MedicineVO>)
    {
        mData = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_medicine, parent, false)
        return MedicineViewHolder(view, mDelegate)

    }
}
