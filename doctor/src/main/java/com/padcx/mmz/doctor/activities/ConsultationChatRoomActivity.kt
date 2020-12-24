package com.padcx.mmz.doctor.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.adapters.ChattingAdapter
import com.padcx.mmz.doctor.adapters.SpecialQuestionAdapter
import com.padcx.mmz.doctor.dialogs.PatientInfoDialog
import com.padcx.mmz.doctor.mvp.presenters.ChatPresenter
import com.padcx.mmz.doctor.mvp.presenters.impls.ChatPresenterImpl
import com.padcx.mmz.doctor.mvp.views.ChatView
import com.padcx.mmz.doctor.utils.MyUserManager
import com.padcx.mmz.doctor.utils.showCropImage
import com.padcx.mmz.doctor.viewpods.PrescriptionViewPod
import com.padcx.mmz.shared.activities.BaseActivity
import com.padcx.mmz.shared.data.vos.ChatMessageVO
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.utils.DOCTORS
import kotlinx.android.synthetic.main.activity_chat_room.*


/**
 * Created by Myint Myint Zaw on 12/15/2020.
 */
class ConsultationChatRoomActivity : BaseActivity(), ChatView {

    private lateinit var mPresenter: ChatPresenter
    private lateinit var questionAnswerAdapter: SpecialQuestionAdapter
    private  var mConsultationChatVO: ConsultationChatVO= ConsultationChatVO()
    private lateinit var adapter: ChattingAdapter

    private lateinit var mPrescriptionViewPod: PrescriptionViewPod
    private val QUESTION_TEMPLATE_ACTIVITY_REQUEST_CODE = 0
    private var consultationStatus = false

    companion object {
        private const val PARAM_CONSULTATION_CHAT_ID = "Chat Id"
        fun newIntent(context: Context, consultation_chat_id: String): Intent {
            val intent = Intent(context, ConsultationChatRoomActivity::class.java)
            intent.putExtra(PARAM_CONSULTATION_CHAT_ID, consultation_chat_id)
            return intent
        }

    }

    private fun getConsultationChatId() = intent.extras?.getString(PARAM_CONSULTATION_CHAT_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chat_room)
        setUpPresenter()
        setUpRecyclerView()
        setUpActionListeners()
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<ChatPresenterImpl, ChatView>()
        mPresenter.onUiReadyChatting(getConsultationChatId().toString(), this)
    }

    private fun setUpRecyclerView() {
        rvChatting.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = ChattingAdapter(mPresenter)
        rvChatting.adapter = adapter

        rvQuestionAnswer.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        questionAnswerAdapter = SpecialQuestionAdapter("chat")
        rvQuestionAnswer.adapter = questionAnswerAdapter
        rvQuestionAnswer?.setHasFixedSize(false)
    }

    private fun setUpActionListeners() {
        txtBack.setOnClickListener {
            onBackPressed()
        }

        txtSeeMore.setOnClickListener {
            var data = Gson().toJson(mConsultationChatVO)
            mConsultationChatVO?.let {
                val dialog: PatientInfoDialog = PatientInfoDialog.newInstance(data)
                dialog.show(supportFragmentManager, "")
            }
        }

        if(consultationStatus){
            sendLayout.visibility = View.GONE
            startActivity(MainActivity.newIntent(this))
            finish()
        }

        btnSendMessage.setOnClickListener {
            if (consultationStatus) {
                sendLayout.visibility = View.GONE
                startActivity(MainActivity.newIntent(this))
                finish()
                Toast.makeText(this, "ဆွေးနွေးမှု ပြီးဆုံးပါပြီ ", Toast.LENGTH_SHORT).show()
            } else {
                sendLayout.visibility = View.VISIBLE
                if (edtMessage.text.toString().isNotEmpty()) {
                    mPresenter.addTextMessage(
                        edtMessage.text.toString(),
                        getConsultationChatId().toString(),
                        DOCTORS,
                        MyUserManager.getUser().photo.toString(),
                        MyUserManager.getUser().name.toString(),
                        this
                    )
                    edtMessage.text = Editable.Factory.getInstance().newEditable("")
                } else {
                    Toast.makeText(this, "Empty text", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnQuestion.setOnClickListener {
            val intent = Intent(this, QuestionTemplateActivity::class.java)
            startActivityForResult(intent, QUESTION_TEMPLATE_ACTIVITY_REQUEST_CODE)
        }

        btnAttach.setOnClickListener {
            Toast.makeText(this, "Attach is Unavailable Right Now!", Toast.LENGTH_LONG).show()
        }

        btnMedicine.setOnClickListener {
            if (consultationStatus) {
                Toast.makeText(this, "ဆွေးနွေးမှု ပြီးဆုံးပါပြီ ", Toast.LENGTH_SHORT).show()
                sendLayout.visibility = View.GONE
                this.finish()
            } else {
                sendLayout.visibility = View.VISIBLE
                var data = Gson().toJson(mConsultationChatVO)
                mConsultationChatVO?.let {
                    startActivity(
                        PrescriptionActivity.newIntent(
                            this,
                            mConsultationChatVO.doctor_info?.speciality.toString(),
                            data
                        )
                    )
                }
            }
        }

        btnMedicalRecord.setOnClickListener {
            if (consultationStatus) {
                sendLayout.visibility = View.GONE
                Toast.makeText(this, "ဆွေးနွေးမှု ပြီးဆုံးပါပြီ ", Toast.LENGTH_SHORT).show()
            } else {
                sendLayout.visibility = View.VISIBLE
                var data = Gson().toJson(mConsultationChatVO)
                mConsultationChatVO?.let {
                    startActivity(MedicalCommentActivity.newIntent(this, data))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == QUESTION_TEMPLATE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val returnString = data?.getStringExtra("questions")
                edtMessage.text = Editable.Factory.getInstance().newEditable(returnString)
            }
        }
    }


    override fun displayPatientInfo(consultationChatVO: ConsultationChatVO) {
        scrollview.scrollTo(0, scrollview.bottom)
        mConsultationChatVO = consultationChatVO

        txtBack.text = consultationChatVO.patient?.name
        ivUserProfile.showCropImage(consultationChatVO.patient?.photo.toString())
        pname.text = " : " + consultationChatVO.patient?.name
        pdateofBirth.text = " : " + consultationChatVO.patient?.dob
        pheight.text = " : " + consultationChatVO.patient?.height
        pbloodtype.text = " : " + consultationChatVO.patient?.blood_type
        pweight.text = " : " + consultationChatVO.patient?.weight
        pbloodpressure.text = " : " + consultationChatVO.patient?.blood_pressure
        pcomment.text = " : " + consultationChatVO.patient?.allergic_medicine

        consultationChatVO.case_summary?.let {
            questionAnswerAdapter.setNewData(it)
        }
        consultationStatus = consultationChatVO.finish_consultation_status
    }

    override fun displayChatMessageList(list: List<ChatMessageVO>) {
        scrollview.scrollTo(0, scrollview.getChildAt(0).height)
        adapter.setNewData(list.toMutableList())
    }

    override fun displayPrescriptionViewPod(prescriptionList: List<PrescriptionVO>) {
        if (prescriptionList.isNotEmpty()) {

            mPrescriptionViewPod = viewPrescription as PrescriptionViewPod
            mPrescriptionViewPod.setDelegate(mPresenter)

            mPrescriptionViewPod.setPrescriptionData(
                prescriptionList,
                MyUserManager.getUser().photo.toString()
            )

            if (consultationStatus) {
                viewPrescription.visibility = View.VISIBLE
            } else {
                viewPrescription.visibility = View.GONE
            }

        }
    }

    override fun showError(error: String) {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(MainActivity.newIntent(this))
        this.finish()
    }

    override fun onResume() {
        super.onResume()
        setUpPresenter()
    }


}