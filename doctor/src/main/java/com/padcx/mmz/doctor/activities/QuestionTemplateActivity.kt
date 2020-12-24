package com.padcx.mmz.doctor.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.adapters.QuestionTemplateAdapter
import com.padcx.mmz.doctor.mvp.presenters.GeneralQuestionTemplatePresenter
import com.padcx.mmz.doctor.mvp.presenters.impls.GeneralQuestionTemplatePresenterImpl
import com.padcx.mmz.doctor.mvp.views.GeneralQuestionTemplateView
import com.padcx.mmz.shared.activities.BaseActivity
import com.padcx.mmz.shared.data.vos.GeneralQuestionTemplateVO
import kotlinx.android.synthetic.main.activity_question_template.*

/**
 * Created by Myint Myint Zaw on 12/18/2020.
 */
class QuestionTemplateActivity: BaseActivity() , GeneralQuestionTemplateView {

    private lateinit var mPresenter: GeneralQuestionTemplatePresenter
    private lateinit var adapter: QuestionTemplateAdapter

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, QuestionTemplateActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_template)
        setUpPresenter()
        setUpRecyclerView()
        setUpActionListeners()
    }


    private fun setUpActionListeners() {
        tvBack.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        sendDataToPreviousPage("")
    }

    private fun setUpRecyclerView() {
        rvGeneralQuestion.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = QuestionTemplateAdapter(mPresenter)
        rvGeneralQuestion.adapter = adapter
    }

    fun sendDataToPreviousPage(questions : String)
    {
        val intent = Intent()
        intent.putExtra("questions" , questions)
        setResult(Activity.RESULT_OK , intent)
        finish()
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<GeneralQuestionTemplatePresenterImpl, GeneralQuestionTemplateView>()
        mPresenter.onUiReady(this,this)
    }

    override fun displayGeneralQuestions(list: List<GeneralQuestionTemplateVO>) {
        adapter.setNewData(list.toMutableList())
    }

    override fun navigateToToChatRoom(questions: String) {
        sendDataToPreviousPage(questions)
    }

    override fun showError(error: String) {

    }
}