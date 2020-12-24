package com.padcx.mmz.myhealthcare.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.activities.ConsultationChatRoomActivity
import com.padcx.mmz.myhealthcare.adapters.ChatHistoryAdapter
import com.padcx.mmz.myhealthcare.dialogs.PrescriptionDialog
import com.padcx.mmz.myhealthcare.mvp.presenters.ChatHistoryPresenter
import com.padcx.mmz.myhealthcare.mvp.presenters.impls.ChatHistoryPresenterImpl
import com.padcx.mmz.myhealthcare.mvp.views.ChatHistoryView
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_consultation.*

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class ConsultationFragment : BaseFragment(), ChatHistoryView {

    private lateinit var mPresenter: ChatHistoryPresenter

    private lateinit var historyadapter: ChatHistoryAdapter

    companion object {
        lateinit var mContext: Context
        fun newInstance(context: Context): ConsultationFragment {
            mContext = context
            return ConsultationFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_consultation, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        rvChatHistory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        historyadapter = ChatHistoryAdapter(mPresenter)
        rvChatHistory?.adapter = historyadapter
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<ChatHistoryPresenterImpl, ChatHistoryView>()
        activity?.let { mPresenter.onUiReady(it, this) }
    }

    override fun displayChatHistoryList(list: List<ConsultationChatVO>) {
        if (list.isNotEmpty()) {
            rvChatHistory.visibility = View.VISIBLE
            empty_view.visibility = View.GONE
        } else {
            rvChatHistory.visibility = View.GONE
            empty_view.visibility = View.VISIBLE
        }
        historyadapter.setNewData(list.toMutableList())
    }

    override fun nextPageToChatRoom(chatId: String) {
        startActivity(activity?.let { ConsultationChatRoomActivity.newIntent(it, chatId) })
    }

    override fun showPrescriptionDialog(
        finishConsultation: Boolean,
        chatID: String,
        pname: String,
        startDate: String
    ) {
        if(finishConsultation)
        {
            val dialog: PrescriptionDialog = PrescriptionDialog.newInstance(chatID, pname, startDate)
            fragmentManager?.let { dialog.show(it, "") }

        }else {
            Toast.makeText(activity,"ဆေးညွန်းမရှိသေးပါ", Toast.LENGTH_SHORT).show()
        }
    }
}