package com.padcx.mmz.myhealthcare.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.adapters.CheckoutAdpater
import com.padcx.mmz.myhealthcare.adapters.ShippingAddressAdapter
import com.padcx.mmz.myhealthcare.dialogs.CheckOutDialog
import com.padcx.mmz.myhealthcare.mvp.presenters.CheckoutPresenter
import com.padcx.mmz.myhealthcare.mvp.presenters.impls.CheckoutPresenterImpl
import com.padcx.mmz.myhealthcare.mvp.views.CheckOutView
import com.padcx.mmz.myhealthcare.utils.MyUserManager
import com.padcx.mmz.shared.activities.BaseActivity
import com.padcx.mmz.shared.data.vos.ConsultationChatVO
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.add_shipping_bottonsheet.view.*


class CheckoutActivity : BaseActivity(), CheckOutView {

    private lateinit var mPresenter: CheckoutPresenter
    private lateinit var adapter: CheckoutAdpater
    private lateinit var shippingAdapter: ShippingAddressAdapter
    private lateinit var consultation_chat_id: String
    private lateinit var mConsultationChatVO: ConsultationChatVO
    private lateinit var prescriptionList: List<PrescriptionVO>
    private lateinit var totalPrice: String
    private  var state: String=""
    private  var township: String=""
    private  var address: String=""
    private var previousPosition: Int = -1
    private  var shippingList: List<String> =ArrayList()

    companion object {
        const val PARM_CONSULTATION_CHAT_ID = "chat id"
        private const val ConsultationCHAT = "ConsultationCHAT"

        fun newIntent(
            context: Context,
            consultation_chat_id: String,
            consultationChatVO: String
        ): Intent {
            val intent = Intent(context, CheckoutActivity::class.java)
            intent.putExtra(PARM_CONSULTATION_CHAT_ID, consultation_chat_id)
            intent.putExtra(ConsultationCHAT, consultationChatVO)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        consultation_chat_id = intent.getStringExtra(PARM_CONSULTATION_CHAT_ID).toString()
        var data = intent?.getStringExtra(ConsultationCHAT)
        mConsultationChatVO = Gson().fromJson(data, ConsultationChatVO::class.java)

        setUpPresenter()
        setUpRecyclerView()
        setUpActionListeners()


    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    private fun setUpActionListeners() {

        checkoutback.setOnClickListener {
            onBackPressed()
        }
        add_address.setOnClickListener {
            showBottomSheetDialog()
        }
        btn_order.setOnClickListener {

            mConsultationChatVO?.let {
                mPresenter.onTapCheckout(
                    prescriptionList = prescriptionList,
                    address,
                    doctorVO = it?.doctor_info,
                    patientVO = it?.patient,
                    total_price = totalPrice
                )
            }

        }

    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<CheckoutPresenterImpl, CheckOutView>()
        mPresenter.onUiReady(this, this)
        mPresenter.onUiReadyCheckout(consultation_chat_id, this)
    }

    override fun displayPrescription(list: List<PrescriptionVO>) {
        if (list.isNotEmpty()) {
            prescriptionList = list
            adapter.setNewData(list.toMutableList())
            var totalamount: Int = 0
            for (item in list) {
                totalamount += item.price.toInt()
            }
            total_amount.text = "${totalamount} ကျပ်"
            totalPrice = "${totalamount} ကျပ်"

        }
    }

    override fun displayShippingAddress(list: List<String>) {
        if (list.isNotEmpty()) {
            shippingList = list
            shippingAdapter.setNewData(list.toMutableList())
        } else {

        }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.add_shipping_bottonsheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)

        view.state_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                state = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        view.township_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                township = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        view.btn_add.setOnClickListener {
            address = "${view.ed_address.text}  ၊ ${township} ၊ ${state} "
            var patientVO = MyUserManager.getUser()
            patientVO.address.add(address)
            mPresenter.onTapAddShipping(patientVO)
            dialog.dismiss()
        }
        view.shipping_cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun displayConfirmDialog(
        list: List<PrescriptionVO>,
        address: String,
        total_price: String
    ) {
        var data = Gson().toJson(prescriptionList)
        val dialog: CheckOutDialog = CheckOutDialog.newInstance(this,data, address, total_price)
        dialog.show(supportFragmentManager, "")
    }


    override fun selectedShippingAddress(mAddress: String, mpreviousPosition: Int) {

        address = mAddress
        btn_order.visibility = View.VISIBLE
        previousPosition = mpreviousPosition
        shippingAdapter = ShippingAddressAdapter(mPresenter, previousPosition)
        shippingList?.let {
            shippingAdapter.setNewData(shippingList.toMutableList())
            address_rc?.adapter = shippingAdapter
        }

    }

    override fun showError(error: String) {

    }


    private fun setUpRecyclerView() {
        prescription_rct?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = CheckoutAdpater(mPresenter)
        prescription_rct?.adapter = adapter
        prescription_rct?.setHasFixedSize(false)

        address_rc?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        shippingAdapter = ShippingAddressAdapter(mPresenter, previousPosition)
        address_rc?.adapter = shippingAdapter
        address_rc?.setHasFixedSize(false)

    }

}