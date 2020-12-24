package com.padcx.mmz.myhealthcare.viewholders

import android.view.View
import com.padcx.mmz.myhealthcare.delegates.ChatRoomDelegate
import com.padcx.mmz.myhealthcare.utils.showCropImage
import com.padcx.mmz.shared.data.vos.ChatMessageVO
import kotlinx.android.synthetic.main.chat_listitem_patient.view.*

class PatientChatViewHolder(itemView: View, private val mDelegate: ChatRoomDelegate) :
    BaseChatViewHolder(itemView) {
    override fun bindData(data: ChatMessageVO) {

        itemView.ivUserPhoto.showCropImage(data.sendBy?.photo.toString())
        itemView.txtTimestamp.text = data.sendBy?.arrived_time
        itemView.txtMessageBody.text = data.messageText
    }
}

