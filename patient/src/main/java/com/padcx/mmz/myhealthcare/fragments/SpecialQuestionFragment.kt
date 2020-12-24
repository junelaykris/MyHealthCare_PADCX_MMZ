package com.padcx.mmz.myhealthcare.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.activities.CaseSummaryActivity
import com.padcx.mmz.myhealthcare.adapters.SpecialQuestionAdapter
import com.padcx.mmz.myhealthcare.dialogs.ConsultationConfirmDialogFragment
import com.padcx.mmz.myhealthcare.dialogs.ConsultationConfirmDialogFragment.Companion.BUNDLE_DOCTOR
import com.padcx.mmz.myhealthcare.dialogs.ConsultationConfirmDialogFragment.Companion.BUNDLE_NAME
import com.padcx.mmz.myhealthcare.dialogs.ConsultationConfirmDialogFragment.Companion.BUNDLE_PATIENT
import com.padcx.mmz.myhealthcare.dialogs.ConsultationConfirmDialogFragment.Companion.BUNDLE_QUESTION
import com.padcx.mmz.myhealthcare.mvp.presenters.CaseSummaryPresenter
import com.padcx.mmz.myhealthcare.mvp.presenters.impls.CaseSummaryPresenterImpl
import com.padcx.mmz.myhealthcare.mvp.views.CaseSummaryView
import com.padcx.mmz.myhealthcare.utils.MyUserManager
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO
import com.padcx.mmz.shared.data.vos.SpecialQuestionVO
import com.padcx.mmz.shared.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_specialquestion.*

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class SpecialQuestionFragment : BaseFragment(), CaseSummaryView {
    lateinit var mContext: CaseSummaryActivity

    private lateinit var mPresenter: CaseSummaryPresenter

    private lateinit var adapter: SpecialQuestionAdapter
    private  var questionAnswerList : ArrayList<QuestionAnswerVO> = ArrayList()

    companion object {
        private const val ARG_PARAM = "speciality"
        private const val ARG_PARAM_DOCTOR = "ARG_PARAM_DOCTOR"
        lateinit var mContext: Context
        var mspeciality: String? = ""
        var mDoctorVO: String? = ""
        fun newInstance(context: Context, speciality: String?, doctorVO: String?): SpecialQuestionFragment {
            mContext = context
            mspeciality = speciality
            mDoctorVO=doctorVO
            return SpecialQuestionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_specialquestion, container, false)
        arguments?.let {
            mspeciality = it.getString(ARG_PARAM)
            mDoctorVO=it.getString(ARG_PARAM_DOCTOR)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpRecyclerView()
        setUpActionListener()
    }

    private fun setUpActionListener() {
        btnConfirm.setOnClickListener {
            val patient = MyUserManager.getUser()
            showCaseSummaryConfirmDialog(patient)
        }
    }

    private fun showCaseSummaryConfirmDialog(patient: PatientVO) {
        val confirmDialog = ConsultationConfirmDialogFragment.newFragment()
        var mdoctorVO = Gson().fromJson(mDoctorVO, DoctorVO::class.java)
        val bundle = Bundle()
        bundle.putString(BUNDLE_NAME, mspeciality)
        bundle.putSerializable(BUNDLE_PATIENT,patient)
        bundle.putSerializable(BUNDLE_DOCTOR,mdoctorVO)
        bundle.putSerializable(BUNDLE_QUESTION,questionAnswerList)
        confirmDialog.arguments = bundle
        confirmDialog.show(childFragmentManager, ConsultationConfirmDialogFragment.TAG_ADD_CONFIRM_DIALOG)
    }

    private fun setUpPresenter() {
        activity?.let{
            mPresenter = getPresenter<CaseSummaryPresenterImpl, CaseSummaryView>()
            mPresenter.onUiReadyForSpecialQuestion(it, mspeciality.toString(),this)
        }
    }

    private fun setUpRecyclerView() {
        rvSpecialQuestion.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = SpecialQuestionAdapter(mPresenter)
        rvSpecialQuestion.adapter = adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context as CaseSummaryActivity
    }

    override fun displaySpecialQuestions(list: List<SpecialQuestionVO>) {
        adapter.setNewData(list.toMutableList())
        questionAnswerList.clear()
        for(item in list)
        {
            questionAnswerList.add(QuestionAnswerVO(item.id,item.question,""))
        }
        adapter.setQuestionAnswerList(questionAnswerList)
    }

    override fun displayOnceGeneralQuestion() {

    }

    override fun displayAlwaysGeneralQuestion() {

    }

    override fun replaceQuestionAnswerList(position: Int, questionAnswerVO: QuestionAnswerVO) {
        questionAnswerList.set(position,questionAnswerVO)
    }

    override fun displayPatientData(patientVO: PatientVO) {

    }


}