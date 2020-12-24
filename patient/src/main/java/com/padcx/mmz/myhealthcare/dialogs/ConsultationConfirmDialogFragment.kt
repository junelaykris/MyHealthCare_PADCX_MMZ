package com.padcx.mmz.myhealthcare.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.adapters.SpecialQuestionAnswerAdapter
import com.padcx.mmz.myhealthcare.mvp.presenters.CaseSummaryPresenter
import com.padcx.mmz.myhealthcare.mvp.presenters.impls.CaseSummaryPresenterImpl
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO
import kotlinx.android.synthetic.main.casesummary_confirm_dialog.*
import kotlinx.android.synthetic.main.casesummary_confirm_dialog.view.*


/**
 * Created by Myint Myint Zaw on 12/14/2020.
 */
class ConsultationConfirmDialogFragment : DialogFragment() {

    private lateinit var mPresenter: CaseSummaryPresenter
    private lateinit var questionAnswerAdapter: SpecialQuestionAnswerAdapter

    companion object {
        const val TAG_ADD_CONFIRM_DIALOG = "TAG_ADD_CONFIRM_DIALOG"
        const val  BUNDLE_PATIENT = "BUNDLE_PATIENT"
        const val  BUNDLE_DOCTOR = "BUNDLE_DOCTOR"
        const val  BUNDLE_QUESTION = "BUNDLE_QUESTION"
        const val BUNDLE_NAME = "BUNDLE_NAME"
        fun newFragment(): ConsultationConfirmDialogFragment {
            return ConsultationConfirmDialogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.casesummary_confirm_dialog, container, false)
        return view
    }

    private fun getSpecialityName() =arguments?.getString(BUNDLE_NAME)
    private fun getPatientVO() =arguments?.getSerializable(BUNDLE_PATIENT) as PatientVO
    private fun getDoctorVO() =arguments?.getSerializable(BUNDLE_DOCTOR) as DoctorVO
    private fun getQuestionAnswerList() =arguments?.getSerializable(BUNDLE_QUESTION) as ArrayList<QuestionAnswerVO>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()

        // getting the bundle back from the android

        view.txtPName.text = getPatientVO().name
        view.txtBloodPressure.text = getPatientVO().blood_pressure
        view.txtBloodType.text = getPatientVO().blood_type
        view.txtDob.text = getPatientVO().dob
        view.txtRemark.text = getPatientVO().allergic_medicine
        view.txtWeight.text = getPatientVO().weight
        view.txtHeight.text = getPatientVO().height


        rvQuestionAnswer.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        questionAnswerAdapter = SpecialQuestionAnswerAdapter("")
        rvQuestionAnswer.adapter = questionAnswerAdapter

       /* rvQuestionAnswer.setHasFixedSize(false)*/

        questionAnswerAdapter.setNewData(getQuestionAnswerList())

        view.btnConsultationConfirm.setOnClickListener {
            mPresenter.onTapSendBroadCast(context,getSpecialityName(),getQuestionAnswerList(),getPatientVO(),getDoctorVO())
            dismiss()
            activity?.finish()
        }

    }

    private fun setUpPresenter() {
        activity?.let {
            mPresenter = ViewModelProviders.of(it).get(CaseSummaryPresenterImpl::class.java)
        }
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
}