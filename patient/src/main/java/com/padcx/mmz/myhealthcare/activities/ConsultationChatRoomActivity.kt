package com.padcx.mmz.myhealthcare.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.adapters.ChattingAdapter
import com.padcx.mmz.myhealthcare.adapters.SpecialQuestionAnswerAdapter
import com.padcx.mmz.myhealthcare.mvp.presenters.ChatPresenter
import com.padcx.mmz.myhealthcare.mvp.presenters.impls.ChatPresenterImpl
import com.padcx.mmz.myhealthcare.mvp.views.ChatView
import com.padcx.mmz.myhealthcare.utils.MyUserManager
import com.padcx.mmz.myhealthcare.utils.showCropImage
import com.padcx.mmz.myhealthcare.viewpods.PrescriptionViewPod
import com.padcx.mmz.shared.activities.BaseActivity
import com.padcx.mmz.shared.data.vos.ChatMessageVO
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.utils.PATIENTS
import kotlinx.android.synthetic.main.activity_chat_room.*

/**
 * Created by Myint Myint Zaw on 12/17/2020.
 */
class ConsultationChatRoomActivity : BaseActivity(), ChatView {

    private lateinit var mPresenter: ChatPresenter
    private lateinit var questionAnswerAdapter: SpecialQuestionAnswerAdapter
    private var mConsultationChatVO: ConsultationChatVO = ConsultationChatVO()
    private lateinit var adapter: ChattingAdapter

    private lateinit var mPrescriptionViewPod: PrescriptionViewPod
    private val QUESTION_TEMPLATE_ACTIVITY_REQUEST_CODE = 0
    private var consultationStatus = false
    var prescriptionViewShow = false

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
        questionAnswerAdapter = SpecialQuestionAnswerAdapter("chat")
        rvQuestionAnswer.adapter = questionAnswerAdapter

    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
        startActivity(MainActivity.newIntent(this))

    }

    private fun setUpActionListeners() {
        txtBack.setOnClickListener {
            onBackPressed()
        }

        txtSeeMore.setOnClickListener {
            Toast.makeText(this, "Tap See More", Toast.LENGTH_LONG).show()
            var data = Gson().toJson(mConsultationChatVO)
            /* val dialog: PatientInfoDialog = PatientInfoDialog.newInstance(data)
             dialog.show(supportFragmentManager, "")*/
        }


        btnAttach.setOnClickListener {
            Toast.makeText(this, "Attach is Unavailable Right Now!", Toast.LENGTH_LONG).show()
        }

        if (mConsultationChatVO.finish_consultation_status){
            sendLayout.visibility=View.GONE
            val intent =MainActivity.startHistoryFragment(this, R.id.action_chat)
            startActivity(intent)
            this.finish()
        }


        btnSendMessage.setOnClickListener {
            mConsultationChatVO?.let {
                if (mConsultationChatVO.finish_consultation_status) {
                    Toast.makeText(
                        this,
                        "ဆွေးနွေးမှု ပြီးဆုံးပါပြီ စာပို့လို့မရနိုင်တော့ပါ",
                        Toast.LENGTH_SHORT
                    ).show()
                    sendLayout.visibility=View.GONE
                } else {
                    sendLayout.visibility=View.VISIBLE
                    if (edtMessage.text.toString().isNotEmpty()) {
                        mPresenter.addTextMessage(
                            edtMessage.text.toString(),
                            getConsultationChatId().toString(),
                            PATIENTS,
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
        }
        /* btnSendMessage.setOnClickListener {
             mPresenter.addTextMessage(
                 edtMessage.text.toString(),
                 getConsultationChatId().toString(),
                 PATIENTS,
                 MyUserManager.getUser().photo.toString(),
                 MyUserManager.getUser().name.toString(),
                 this
             )

         }*/
    }


    override fun displayPatientInfo(consultationChatVO: ConsultationChatVO) {
        scrollview.scrollTo(0, scrollview.bottom)
        prescriptionViewShow = consultationChatVO.finish_consultation_status
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

        /*  if (consultationStatus) {
              viewPrescription.visibility = View.VISIBLE
          } else {
              viewPrescription.visibility = View.GONE
          }*/

    }

    override fun displayChatMessageList(list: List<ChatMessageVO>) {
        scrollview.scrollTo(0, scrollview.getChildAt(0).height)
        adapter.setNewData(list.toMutableList())
    }

    override fun displayPrescriptionViewPod(prescriptionList: List<PrescriptionVO>) {
        if (prescriptionList.isNotEmpty()) {

            mPrescriptionViewPod = viewPrescription as PrescriptionViewPod
            mPrescriptionViewPod.setDelegate(mPresenter)

            mConsultationChatVO?.let {
                mPrescriptionViewPod.setPrescriptionData(
                    prescriptionList,
                    mConsultationChatVO.doctor_info?.photo.toString(),
                    mConsultationChatVO.id.toString()
                )
            }

            if (prescriptionList.size > 0) {
                viewPrescription.visibility = View.VISIBLE
            } else {
                viewPrescription.visibility = View.GONE
            }

        }
    }

    override fun nextPageToCheckout(chatId: String) {
        mConsultationChatVO?.let {
            var data = Gson().toJson(mConsultationChatVO)
            startActivity(CheckoutActivity.newIntent(this, chatId, data))
        }
    }

    override fun showError(error: String) {
    }

    override fun onResume() {
        super.onResume()
        setUpPresenter()
    }
}