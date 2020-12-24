package com.padcx.mmz.myhealthcare.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.activities.CaseSummaryActivity
import com.padcx.mmz.myhealthcare.mvp.presenters.CaseSummaryPresenter
import com.padcx.mmz.myhealthcare.mvp.presenters.impls.CaseSummaryPresenterImpl
import com.padcx.mmz.myhealthcare.mvp.views.CaseSummaryView
import com.padcx.mmz.myhealthcare.utils.MyUserManager
import com.padcx.mmz.shared.data.vos.PatientVO
import com.padcx.mmz.shared.data.vos.QuestionAnswerVO
import com.padcx.mmz.shared.data.vos.SpecialQuestionVO
import com.padcx.mmz.shared.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_general_question.*

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class GeneralQuestionFragment : BaseFragment(), CaseSummaryView {
    lateinit var mContext: CaseSummaryActivity

    private lateinit var mPresenter: CaseSummaryPresenter

    private var year: String? = ""
    private var month: String? = ""
    private var day: String? = ""
    private var bloodType: String = ""
    private var dob: String = ""
    private var height: String = ""
    private var allergicMedicine: String = ""
    private var weight: String = ""
    private var bloodPressure: String = ""
    private var showInfo: Boolean = false

    companion object {
        lateinit var mContext: Context
        fun newInstance(context: Context): GeneralQuestionFragment {
            mContext = context
            return GeneralQuestionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_general_question, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpActionListener()
        setUpItemSelectedListener()
    }

    private fun setUpItemSelectedListener() {
        spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                year = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                month = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                day = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spinnerBloodType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                bloodType = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setUpActionListener() {

        btnNext.setOnClickListener {
            if (MyUserManager.getUser().blood_type.toString().isNotEmpty()) {
                edtHeight.text =
                    Editable.Factory.getInstance().newEditable(MyUserManager.getUser().height)
                edtComment.text = Editable.Factory.getInstance()
                    .newEditable(MyUserManager.getUser().allergic_medicine)
            }

            height = edtHeight.text.toString()
            allergicMedicine = edtComment.text.toString()
            weight = edtWeight.text.toString()
            bloodPressure = edtBloodPressure.text.toString()
            dob = "$day $month $year"


            if (height.isNotEmpty() && allergicMedicine.isNotEmpty() && weight.isNotEmpty() && bloodPressure.isNotEmpty()) {
                if (MyUserManager.getUser().blood_type.toString().isEmpty()) {
                    MyUserManager.getUser().dob = "$day $month $year"
                    MyUserManager.getUser().blood_type = bloodType
                }

                MyUserManager.getUser().height = height
                MyUserManager.getUser().allergic_medicine = allergicMedicine
                MyUserManager.getUser().weight = weight
                MyUserManager.getUser().blood_pressure = bloodPressure

                displayAlwaysGeneralQuestion()
                nextPageToSpecialQuestion()
            } else {
                mContext.showSnackbar("Please fill required field")
            }

            /* if (height.isNotEmpty() && allergicMedicine.isNotEmpty() && weight.isNotEmpty() && bloodPressure.isNotEmpty() && dob.isNotEmpty() &&
                 bloodType.isNotEmpty()
             ) {

                 val patientVO = PatientVO()
                 patientVO.dob = dob
                 patientVO.blood_type = bloodType
                 patientVO.blood_pressure = bloodPressure
                 patientVO.height = height
                 patientVO.weight = weight
                 patientVO.allergic_medicine = allergicMedicine
                 patientVO.id = MyUserManager.getUser().id
                 patientVO.deviceId = MyUserManager.getUser().deviceId
                 patientVO.photo = MyUserManager.getUser().photo
                 patientVO.email = MyUserManager.getUser().email
                 patientVO.id = MyUserManager.getUser().id
                 patientVO.name= MyUserManager.getUser().name

                 MyUserManager.saveUser(patientVO)

                 displayAlwaysGeneralQuestion()
                  nextPageToSpecialQuestion()

             } else {
                 mContext.showSnackbar("Please fill required field")
             }*/
        }
    }

    private fun nextPageToSpecialQuestion() {
        btnNext.setOnClickListener {
            mContext.swapCurrentFragment("1")
        }

    }

    private fun setUpPresenter() {
        activity?.let {
            mPresenter = getPresenter<CaseSummaryPresenterImpl, CaseSummaryView>()
            mPresenter.onUiReadyForGeneralQuestion(
                it,
                MyUserManager.getUser().email.toString(),
                this
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context as CaseSummaryActivity
    }

    override fun displaySpecialQuestions(list: List<SpecialQuestionVO>) {

    }

    override fun displayOnceGeneralQuestion() {
        cvUserInfo.visibility = View.GONE
        llOneTimeQuestion.visibility = View.VISIBLE
    }

    override fun displayAlwaysGeneralQuestion() {
        cvUserInfo.visibility = View.VISIBLE
        llOneTimeQuestion.visibility = View.GONE

        txtUserInfo.text = resources.getString(R.string.patient_name) + "  :  " +
                MyUserManager.getUser().name + "\n" + resources.getString(R.string.dob) +
                "  :  " +  MyUserManager.getUser().dob + "\n" + resources.getString(R.string.patient_height) + "  :  " +
                MyUserManager.getUser().height + "\n" + resources.getString(R.string.patient_blood_type) + "  :  " +
                MyUserManager.getUser().blood_type + "\n" + resources.getString(R.string.allergicMedicine) + "  :  " +
                MyUserManager.getUser().allergic_medicine


    }

    override fun replaceQuestionAnswerList(position: Int, questionAnswerVO: QuestionAnswerVO) {

    }

    override fun displayPatientData(patientVO: PatientVO) {
        /* txtUserInfo.text = resources.getString(R.string.patient_name) + "  :  " +
                 patientVO.name +"\n" + resources.getString(R.string.dob) +
                 "  :  " + patientVO.dob +"\n" + resources.getString(R.string.patient_height) + "  :  " +
                 patientVO.height +"\n"+ resources.getString(R.string.patient_blood_type) + "  :  " +
                 patientVO.blood_type +"\n"+ resources.getString(R.string.allergicMedicine) + "  :  " +
                 patientVO.allergic_medicine*/
    }
}