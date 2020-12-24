package com.padcx.mmz.doctor.viewholders

import android.view.View
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.delegates.MedicineDelegate
import com.padcx.mmz.shared.data.vos.MedicineVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.listitem_medicine.view.*

class MedicineViewHolder(itemView: View, private val mDelegate: MedicineDelegate) :
        BaseViewHolder<MedicineVO>(itemView) {

    override fun bindData(data: MedicineVO) {

        data?.let {
            itemView.tvMedicineName.text = data.name
            if(data.isSelected == false)
            {
                itemView.btnSelected.setImageResource(R.drawable.add)
            }else
            {
                itemView.btnSelected.setImageResource(R.drawable.remove)
            }
        }

        itemView.btnSelected.setOnClickListener {
            if(data.isSelected ==false) {

                data?.let {
                        itemView.btnSelected.setImageResource(R.drawable.remove)
                        mDelegate.onTapSelectMedicine(data)
                        data.isSelected= true
                }
            }
            else{
                itemView.btnSelected.setImageResource(R.drawable.add)
                data.isSelected =false
                mDelegate.onTapRemoveMedicine(data)
            }

        }
    }
}