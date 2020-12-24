package com.padcx.mmz.myhealthcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.delegates.ChatHistoryDelegate
import com.padcx.mmz.myhealthcare.viewholders.ChatHistoryViewHolder
import com.padcx.mmz.shared.adapters.BaseRecyclerAdapter
import com.padcx.mmz.shared.data.vos.ConsultationChatVO


class ChatHistoryAdapter(private val mDelegate: ChatHistoryDelegate) :
        BaseRecyclerAdapter<ChatHistoryViewHolder, ConsultationChatVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHistoryViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_chathistory, parent, false)
        return ChatHistoryViewHolder(view, mDelegate)

    }
}
