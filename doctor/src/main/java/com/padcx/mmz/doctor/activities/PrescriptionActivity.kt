package com.padcx.mmz.doctor.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.adapters.MedicineAdapter
import com.padcx.mmz.doctor.mvp.presenters.impls.PrescriptionPresenterImpl
import com.padcx.mmz.doctor.mvp.views.PrescriptionView
import com.padcx.mmz.shared.activities.BaseActivity
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.MedicineVO
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.data.vos.RoutineVO
import kotlinx.android.synthetic.main.activity_prescription.*
import kotlinx.android.synthetic.main.dialog_routine.view.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Myint Myint Zaw on 12/18/2020.
 */
class PrescriptionActivity : BaseActivity(), PrescriptionView {

    private lateinit var mPresenter: PrescriptionPresenterImpl
    private lateinit var adapter: MedicineAdapter
    private lateinit var speciality: String
    private var medicinelist: List<MedicineVO> = arrayListOf()
    private var filterlist: ArrayList<MedicineVO> = arrayListOf()
    private var prescriptionList: ArrayList<PrescriptionVO> = arrayListOf()

    private lateinit var mConsultationChatVO: ConsultationChatVO

    companion object {
        private const val PARAM_SPECIALITY = "speciality"
        private const val consultationCHATVO = "ConsultationCHATVO"

        fun newIntent(context: Context, speciality: String, consultationChatVO: String): Intent {
            val intent = Intent(context, PrescriptionActivity::class.java)
            intent.putExtra(PARAM_SPECIALITY, speciality)
            intent.putExtra(consultationCHATVO, consultationChatVO)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)
        var data = intent?.getStringExtra(consultationCHATVO)
        mConsultationChatVO = Gson().fromJson(data, ConsultationChatVO::class.java)
        speciality = intent.getStringExtra(PARAM_SPECIALITY).toString()
        setUpPresenter()
        setUpRecyclerView()
        setUpActionListeners()


    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<PrescriptionPresenterImpl, PrescriptionView>()
        mPresenter.onUiReady(this, this)

        speciality?.let {
            mPresenter.onUiReadyForPrescription(speciality)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
       /* mConsultationChatVO?.let {
            startActivity(
                ConsultationChatRoomActivity.newIntent(
                    this,
                    consultation_chat_id = mConsultationChatVO.id.toString()
                )
            )
        }
        this.finish()*/
    }

    private fun setUpRecyclerView() {
        rvMedicine.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MedicineAdapter(mPresenter)
        rvMedicine.adapter = adapter
    }

    private fun setUpActionListeners() {

        edtSearchMedicine.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })

        txtBackPress.setOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            if (prescriptionList.size > 0) {
                mPresenter.onTapFinishConsultation(prescriptionList, mConsultationChatVO)
            }
        }
    }

    fun filter(text: String) {
        filterlist.clear()
        medicinelist?.let {

            for (item in medicinelist) {
                var st = item.name.toString().toLowerCase()
                if (st.contains(text)) {
                    filterlist.add(item)
                }
            }
            adapter.setMedicineList(filterlist)
        }

    }

    override fun displayMedicineList(medicinelist: List<MedicineVO>) {
        adapter.setNewData(medicinelist.toMutableList())
    }

    override fun displayRoutineChooseDialog(medicineVO: MedicineVO) {
        showMedicineDialog(medicineVO)
    }

    private fun showMedicineDialog(medicineVO: MedicineVO) {
        var morningstatus = true
        var afternoonstatus = true
        var nightstatus = true

        var number = 1
        var daycount: Int = 0
        var tabcount: String = "1"
        var eatingtime: String = ""
        var daystemp: String = ""
        var count = 0

        val view = layoutInflater.inflate(R.layout.dialog_routine, null)
        val txt_tabcount = view?.findViewById<TextView>(R.id.txtCount)
        val pt_comment = view?.findViewById<EditText>(R.id.txtRemark)

        val dialog = this?.let { Dialog(it) }

        dialog?.apply {
            setCancelable(false)
            setContentView(view)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        view.txtMedicineName.text = medicineVO.name
        txt_tabcount?.text = tabcount

        view.txtMorning.setOnClickListener {

            morningstatus = if (morningstatus) {
                view.txtMorning.setBackgroundResource(R.drawable.bg_rounded_border_blue)
                view.txtMorning.setTextColor(Color.WHITE)
                count++
                false
            } else {
                view.txtMorning.setBackgroundResource(R.drawable.bg_rounded_border_grey)
                view.txtMorning.setTextColor(Color.BLACK)
                count--
                true
            }

            if (count > -1) {
                var result = number * daycount * count
                txt_tabcount?.text = result.toString()
                tabcount = result.toString()
            }
        }

        view.txtAfternoon.setOnClickListener {
            afternoonstatus = if (afternoonstatus) {
                view.txtAfternoon.setBackgroundResource(R.drawable.bg_rounded_border_blue)
                view.txtAfternoon.setTextColor(Color.WHITE)
                count++
                false
            } else {
                view.txtAfternoon.setBackgroundResource(R.drawable.bg_rounded_border_grey)
                view.txtAfternoon.setTextColor(Color.BLACK)
                count--
                true
            }
            if (count > -1) {
                var result = number * daycount * count
                txt_tabcount?.text = result.toString()
                tabcount = result.toString()
            }
        }

        view.txtNight.setOnClickListener {
            nightstatus = if (nightstatus) {
                view.txtNight.setBackgroundResource(R.drawable.bg_rounded_border_blue)
                view.txtNight.setTextColor(Color.WHITE)
                count++
                false
            } else {
                view.txtNight.setBackgroundResource(R.drawable.bg_rounded_border_grey)
                view.txtNight.setTextColor(Color.BLACK)
                count--
                true
            }
            if (count > -1) {
                var result = number * daycount * count
                txt_tabcount?.text = result.toString()
                tabcount = result.toString()
            }
        }

        view.txtBeforeMeal.setOnClickListener {
            view.txtBeforeMeal.setBackgroundResource(R.drawable.bg_rounded_border_blue)
            view.txtBeforeMeal.setTextColor(Color.WHITE)
            view.txtAfterMeal.setBackgroundResource(R.drawable.bg_rounded_border_grey)
            view.txtAfterMeal.setTextColor(Color.BLACK)
            eatingtime = "အစားမစားမှီသောက်ရန်"
        }

        view.txtAfterMeal.setOnClickListener {
            view.txtAfterMeal.setBackgroundResource(R.drawable.bg_rounded_border_blue)
            view.txtAfterMeal.setTextColor(Color.WHITE)
            view.txtBeforeMeal.setBackgroundResource(R.drawable.bg_rounded_border_grey)
            view.txtBeforeMeal.setTextColor(Color.BLACK)
            eatingtime = "အစားစားပြီးမှ သောက်ရန်"
        }

        view.day_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                var day = parent.getItemAtPosition(position).toString()
                if (day == "Days") {
                    daycount = 1
                    daystemp = " Days"
                } else {
                    daycount = 7
                    daystemp = " Week"
                }

                var result = number * daycount * count
                txt_tabcount?.text = result.toString()
                tabcount = result.toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        view.ed_day.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun afterTextChanged(s: Editable?) {
                var data = s.toString()
                if (data.isNotEmpty()) {
                    number = data.toInt()
                    var result = number * daycount * count
                    view.txtCount.text = result.toString()
                    tabcount = result.toString()
                }
            }
        })


        view.btnConfirm.setOnClickListener {
            // prescription list add
            var days: String = ""
            if (!morningstatus) {
                days += " မနက် ၊ "
            }
            if (!afternoonstatus) {
                days += "နေ့  ၊  "
            }
            if (!nightstatus) {
                days += "ည"
            }


            var routineVO = RoutineVO(
                id = "0",
                amount = medicineVO.price.toString(),
                days = view.ed_day.text.toString() + daystemp,
                comment = pt_comment?.text.toString(),
                quantity = tabcount,
                times = days,
                repeat = eatingtime

            )

            var prescriptionVO = PrescriptionVO(
                id = UUID.randomUUID().toString(),
                count = tabcount,
                medicine_name = medicineVO.name.toString(),
                price = medicineVO.price.toString(),
                routineVO = routineVO
            )
            if (pt_comment?.text.toString().isNotEmpty()) {
                prescriptionList.add(prescriptionVO)
                dialog?.dismiss()
            } else {
                Toast.makeText(
                    this, "အချက်အလက် အားလုံး ပြည့်စုံအောင် ဖြည့်စွက်ပေးရန် လိုနေပါသေး သည်",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        dialog?.show()
    }


    override fun removeMedicine(medicineVO: MedicineVO) {
        var index = 0
        for (item in prescriptionList) {
            if (item.medicine_name == medicineVO.name) {
                prescriptionList.removeAt(index)
            }
            index++
        }
    }

    override fun finishConsultation() {
        onBackPressed()
    }

    override fun showError(error: String) {

    }
}