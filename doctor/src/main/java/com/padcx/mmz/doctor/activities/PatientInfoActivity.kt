package com.padcx.mmz.doctor.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.adapters.SpecialQuestionAdapter
import com.padcx.mmz.doctor.mvp.presenters.PatientInfoPresenter
import com.padcx.mmz.doctor.mvp.presenters.impls.PatientInfoPresenterImpl
import com.padcx.mmz.doctor.utils.showCropImage
import com.padcx.mmz.doctor.views.PatientInfoView
import com.padcx.mmz.shared.activities.BaseActivity
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import kotlinx.android.synthetic.main.activity_patientinfo.*
import kotlinx.android.synthetic.main.default_tool_bar.*

/**
 * Created by Myint Myint Zaw on 12/15/2020.
 */
class PatientInfoActivity : BaseActivity(),PatientInfoView {

    private lateinit var mConsultationRequestVO: ConsultationRequestVO
    private lateinit var questionAnswerAdapter: SpecialQuestionAdapter
    private lateinit var mPresenter: PatientInfoPresenter

    companion object {
        private const val PARAM_CONSULTATION_Request_ID = "consultation_request_id"
        fun newIntent(
            context: Context,
            consultRequestID: String
        ): Intent {
            val intent = Intent(context, PatientInfoActivity::class.java)
            intent.putExtra(PARAM_CONSULTATION_Request_ID, consultRequestID)
            return intent
        }
    }

    private fun getConsultationChatId() =intent.extras?.getString(PARAM_CONSULTATION_Request_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patientinfo)

        setUpPresenter()
        setUpRecyclerView()
        setUpActionListeners()
    }

    private fun setUpActionListeners() {
        toolBarStyle()
    }

    private fun toolBarStyle() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        tvTitle.text = resources.getString(R.string.patient_detail)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpRecyclerView() {
        rvQuestionAnswer?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        questionAnswerAdapter = SpecialQuestionAdapter("")
        rvQuestionAnswer?.adapter = questionAnswerAdapter
    }

    private fun setUpPresenter()
    {
        mPresenter = getPresenter<PatientInfoPresenterImpl, PatientInfoView>()
        mPresenter.onUiReadyConsultation(getConsultationChatId().toString(),this)
    }

    override fun displayPatientInfo(consultationRequestVO: ConsultationRequestVO) {
        ivProfile.showCropImage(consultationRequestVO.patient.photo)
        txtPName.text = " : " + consultationRequestVO.patient.name
        txtDob.text =  " : " +consultationRequestVO.patient.dob
        txtHeight.text =  " : " + consultationRequestVO.patient.height
        txtBloodType.text = " : " + consultationRequestVO.patient.blood_type
        txtWeight.text =  " : " +consultationRequestVO.patient.weight
        txtBloodPressure.text =  " : " +consultationRequestVO.patient.blood_pressure
        txtAllergicMedicine.text =  " : " + consultationRequestVO.patient.allergic_medicine
        mConsultationRequestVO= consultationRequestVO
        questionAnswerAdapter.setNewData(consultationRequestVO.case_summary)

        btnConsultationConfirm.setOnClickListener {
            //Toast.makeText(this,mConsultationRequestVO.doctor_info.id+"", Toast.LENGTH_LONG).show()
            if(mConsultationRequestVO != null) {
                mPresenter.onTapStartConsultation(mConsultationRequestVO)
            }
        }

    }

    override fun startConsultationChat(chatRequestId: String) {
        if(chatRequestId !=null) {
            startActivity(ConsultationChatRoomActivity.newIntent(this,chatRequestId))
            this.finish()

        }
        /*if(chatId!=null) {
            this.finish()
            Toast.makeText(this,"Chat ID is ...."+chatId.consultation_id, Toast.LENGTH_LONG).show()
           *//* startActivity(ConsultationChatRoomActivity.newIntent(this, chatId))*//*
        }*/
    }

    override fun showError(error: String) {

    }

}