package com.padcx.mmz.doctor.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.adapters.SpecialQuestionAdapter
import com.padcx.mmz.doctor.mvp.presenters.ChatPresenter
import com.padcx.mmz.doctor.mvp.presenters.impls.ChatPresenterImpl
import com.padcx.mmz.doctor.mvp.views.ChatView
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.fragment.BaseDialogFragment
import kotlinx.android.synthetic.main.patient_info_dialog.view.*

class PatientInfoDialog : BaseDialogFragment() {

    private lateinit var mPresenter: ChatPresenter

    companion object {

        private const val KEY_CONSULTATION = "consultationChatVO"

        fun newInstance(chatVO: String): PatientInfoDialog {
            val args = Bundle()
            args.putString(KEY_CONSULTATION, chatVO)
            val fragment = PatientInfoDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.patient_info_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog?.apply {
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    private fun setupView(view: View) {
        var data = arguments?.getString(KEY_CONSULTATION)
        val gson = Gson()
        var consultationChatVO = gson.fromJson(data, ConsultationChatVO::class.java)

        view.tvPName.text = " : " + consultationChatVO.patient?.name
        view.tvPDob.text = " : " + consultationChatVO.patient?.dob
        view.tvPHeight.text = " : " + consultationChatVO.patient?.height
        view.tvPBloodType.text = " : " + consultationChatVO.patient?.blood_type
        view.tvPWeight.text = " : " + consultationChatVO.patient?.weight
        view.tvPBloodPressure.text = " : " + consultationChatVO.patient?.blood_pressure
        view.tvPAllergicMedicine.text = " : " + consultationChatVO.patient?.allergic_medicine

        view.rc_question_answer?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mPresenter = getPresenter<ChatPresenterImpl, ChatView>()
        var questionAnswerAdapter = SpecialQuestionAdapter( "")
        view.rc_question_answer?.adapter = questionAnswerAdapter
        view.rc_question_answer?.setHasFixedSize(false)

        consultationChatVO.case_summary?.let {
            questionAnswerAdapter.setNewData(it)
        }
    }

    private fun setupClickListeners(view: View) {
        view.btnClose.setOnClickListener {
            dismiss()
        }
    }

}