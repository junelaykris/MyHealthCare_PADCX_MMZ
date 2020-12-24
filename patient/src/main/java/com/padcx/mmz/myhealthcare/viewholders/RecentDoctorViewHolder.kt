package com.padcx.mmz.myhealthcare.viewholders

import android.view.View
import com.padcx.mmz.myhealthcare.delegates.RecentDoctorViewItemActionDelegate
import com.padcx.mmz.myhealthcare.utils.showCropImage
import com.padcx.mmz.shared.data.vos.RecentDoctorVO
import com.padcx.mmz.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_recent_doctor.view.*

class RecentDoctorViewHolder(
    itemView: View,
    private val mDelegate: RecentDoctorViewItemActionDelegate
) :
    BaseViewHolder<RecentDoctorVO>(itemView) {

    override fun bindData(data: RecentDoctorVO) {
        data?.let {
            itemView.txtName.text = data?.name
            itemView.txtSpeciality.text = data?.speciality
            itemView.ivDoctorProfile.showCropImage(data.photo)
            itemView.cvRecentDoctor.setOnClickListener {
                mDelegate.onTapRecentDoctor(data)
            }
        }
    }
}