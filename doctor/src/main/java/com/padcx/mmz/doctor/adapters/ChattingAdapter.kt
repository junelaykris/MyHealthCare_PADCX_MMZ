package com.padcx.mmz.doctor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.delegates.ChatRoomDelegate
import com.padcx.mmz.doctor.viewholders.BaseChatViewHolder
import com.padcx.mmz.doctor.viewholders.PatientChatViewHolder
import com.padcx.mmz.doctor.viewholders.DoctorChatViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.ChatMessageVO
import com.padcx.mmz.shared.utils.PATIENTS

/**
 * Created by Myint Myint Zaw on 12/16/2020.
 */
class ChattingAdapter(delegate: ChatRoomDelegate) :
    BaseRecyclerAdapter<BaseChatViewHolder, ChatMessageVO>() {
    private val mDelegate: ChatRoomDelegate = delegate

    private val viewTypePatient = 1
    private val viewTypeDoctor = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseChatViewHolder {
        when (viewType) {
            viewTypePatient -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_listitem_patient, parent, false)

                return PatientChatViewHolder(view, mDelegate)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_listitem_doctor, parent, false)

                return DoctorChatViewHolder(view, mDelegate)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        when{
            mData[position].type == PATIENTS ->{
                return viewTypePatient
            }else->{
            return viewTypeDoctor
        }
        }
        /*return when (mData[position].type) {
            PATIENTS -> {
                viewTypePatient
            }
            else -> {
                viewTypeDoctor
            }
        }*/
    }

}