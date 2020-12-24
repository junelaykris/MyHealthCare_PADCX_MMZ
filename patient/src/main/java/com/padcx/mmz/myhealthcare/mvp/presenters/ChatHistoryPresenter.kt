package com.padcx.mmz.myhealthcare.mvp.presenters

import com.padcx.mmz.myhealthcare.delegates.ChatHistoryDelegate
import com.padcx.mmz.myhealthcare.mvp.views.ChatHistoryView
import com.padcx.mmz.shared.presenter.BasePresenter

interface ChatHistoryPresenter : BasePresenter<ChatHistoryView>, ChatHistoryDelegate {

}