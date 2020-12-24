package com.padcx.mmz.doctor.viewholders

import android.view.View
import com.padcx.mmz.doctor.delegates.ChatRoomDelegate
import com.padcx.mmz.doctor.utils.showCropImage
import com.padcx.mmz.shared.data.vos.ChatMessageVO
import kotlinx.android.synthetic.main.chat_listitem_doctor.view.*


class DoctorChatViewHolder(itemView: View, private val mDelegate: ChatRoomDelegate) :
        BaseChatViewHolder(itemView) {
    override fun bindData(data: ChatMessageVO) {

        itemView.ivProfile.showCropImage(data.sendBy?.photo.toString())
        itemView.txtTimeStamp.text = data.sendBy?.arrived_time
        itemView.txtMessage.text = data.messageText
    }
}

