package com.padcx.mmz.doctor.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.gson.Gson
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.adapters.ConsultationHistoryAdapter
import com.padcx.mmz.doctor.adapters.ConsultationRequestAdapter
import com.padcx.mmz.doctor.dialogs.PatientInfoDialog
import com.padcx.mmz.doctor.dialogs.PrescriptionDialog
import com.padcx.mmz.doctor.mvp.presenters.HomePresenter
import com.padcx.mmz.doctor.mvp.presenters.impls.HomePresenterImpl
import com.padcx.mmz.doctor.utils.MyUserManager
import com.padcx.mmz.doctor.utils.showCropImage
import com.padcx.mmz.doctor.views.HomeView
import com.padcx.mmz.shared.activities.BaseActivity
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.data.vos.ConsultedPatientVO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.default_empty_text.*
import kotlinx.android.synthetic.main.medical_record_dialog.view.*
import kotlinx.android.synthetic.main.postpone_dialog.view.*

class MainActivity : BaseActivity(), HomeView {

    private lateinit var mPresenter: HomePresenter

    private lateinit var consultationRequestAdapter: ConsultationRequestAdapter
    private lateinit var consultationHistoryAdapter: ConsultationHistoryAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*FirebaseMessaging.getInstance().subscribeToTopic(MyUserManager.getUser().speciality.toString())
            .addOnCompleteListener { task ->
                var msg = "subscribe success"
                if (!task.isSuccessful) {
                    msg = "subscribe failed"
                }
                Log.d("Message....", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }*/
        
        /*Firebase.messaging.subscribeToTopic(MyUserManager.getUser().speciality)
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
            }*/

        val user = MyUserManager.getUser()
        ivProfile.showCropImage(user.photo)
        tvDoctorName.text = user.name
        Log.e("authentication", user.name ?: "No name found")


        setUpPresenter()
        //setUpActionListeners()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        rvConsultationRequest.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        consultationRequestAdapter = ConsultationRequestAdapter(mPresenter)
        rvConsultationRequest.adapter = consultationRequestAdapter

        rvConsultationHistory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        consultationHistoryAdapter = ConsultationHistoryAdapter(mPresenter)
        rvConsultationHistory.adapter = consultationHistoryAdapter
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<HomePresenterImpl, HomeView>()
        mPresenter.onUiReady(this, this)
    }

    override fun displayConsultationRequests(list: List<ConsultationRequestVO>) {
        consultationRequestAdapter.setNewData(list.toMutableList())
    }

    override fun displayConsultationAcceptHistoryList(list: List<ConsultationRequestVO>) {

    }

    override fun displayConsultedPatient(patientlist: List<ConsultedPatientVO>) {
        consultationRequestAdapter.setConsultedPatientList(patientlist.toMutableList())
    }

    override fun displayPatientInfo(requestId: String) {
        startActivity(PatientInfoActivity.newIntent(this, requestId))
    }

    override fun displayConsultationList(list: List<ConsultationChatVO>) {
        consultationHistoryAdapter.setNewData(list.toMutableList())
        if (list?.size == 0) {
            //empty_view.visibility = View.VISIBLE
            // noDataLayout.visibility = View.VISIBLE
            txtConsultedHistory.visibility = View.GONE
        } else {
           // empty_view.visibility = View.GONE
            txtConsultedHistory.visibility = View.VISIBLE
        }
    }

    override fun displayPostPoneChooserDialog(consultationRequestVO: ConsultationRequestVO) {
        val view = layoutInflater.inflate(R.layout.postpone_dialog, null)
        val dialog = this?.let { Dialog(it) }
        val timePicker = view?.findViewById<TimePicker>(R.id.timePicker)
        var msg: String = ""
        timePicker?.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }

            val h = if (hour < 10) "0" + hour else hour
            val min = if (minute < 10) "0" + minute else minute
            // display format of time
            msg = " $h : $min $am_pm"

        }

        dialog?.apply {
            setCancelable(true)
            setContentView(view)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        view.btnConfirm.setOnClickListener {
            msg?.let { mPresenter.onTapPostponeTime(it, consultationRequestVO) }
            dialog?.dismiss()
        }
        dialog?.show()
    }

    override fun displayPostponseProcessSuccess() {
        Toast.makeText(
            this,
            "ယခု လူနာနှင့် ရက်ချိန်းသတ်မှတ်မှု လုပ်ငန်းစဉ် အောင်မြင်ပါ သည်",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun displayPatientInfoDialog(consultationChatVO: ConsultationChatVO) {
        var data = Gson().toJson(consultationChatVO)
        consultationChatVO?.let {
            val dialog: PatientInfoDialog = PatientInfoDialog.newInstance(data)
            dialog.show(supportFragmentManager, "")
        }
    }

    override fun displayPrescriptionDialog(
        consultationId: String,
        patient_name: String,
        start_conservation_date: String
    ) {
        val dialog: PrescriptionDialog =
            PrescriptionDialog.newInstance(consultationId, patient_name, start_conservation_date)
        dialog.show(supportFragmentManager, "")
    }

    override fun nextPageChatRoom(chatId: String) {
        startActivity(chatId?.let { ConsultationChatRoomActivity.newIntent(this, it) })
        this.finish()
    }

    override fun displayMedicalCommentDialog(consultationChatVO: ConsultationChatVO) {
        val view = layoutInflater.inflate(R.layout.medical_record_dialog, null)
        val dialog = this?.let { Dialog(it) }
        val pname = view?.findViewById<TextView>(R.id.pname)
        val pdateofBirth = view?.findViewById<TextView>(R.id.pdateofBirth)
        val medical_comment = view?.findViewById<TextView>(R.id.pmedical_comment)
        val p_start_date = view?.findViewById<TextView>(R.id.p_start_date)

        pname?.text = consultationChatVO.patient?.name.toString()
        pdateofBirth?.text = consultationChatVO.patient?.dob.toString()
        p_start_date?.text = consultationChatVO.start_consultation_date
        medical_comment?.text = consultationChatVO.medical_record.toString()

        dialog?.apply {
            setCancelable(true)
            setContentView(view)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        view.btn_close.setOnClickListener {
            dialog?.dismiss()
        }
        dialog?.show()
    }

    override fun showError(error: String) {

    }

    override fun onResume() {
        super.onResume()
        setUpPresenter()
    }
}