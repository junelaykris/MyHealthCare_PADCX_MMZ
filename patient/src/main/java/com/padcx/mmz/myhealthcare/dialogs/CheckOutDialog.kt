package com.padcx.mmz.myhealthcare.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.activities.CaseSummaryActivity
import com.padcx.mmz.myhealthcare.activities.CheckoutActivity
import com.padcx.mmz.myhealthcare.activities.MainActivity
import com.padcx.mmz.myhealthcare.adapters.CheckoutAdpater
import com.padcx.mmz.myhealthcare.mvp.presenters.CheckoutPresenter
import com.padcx.mmz.myhealthcare.mvp.presenters.impls.CheckoutPresenterImpl
import com.padcx.mmz.myhealthcare.mvp.views.CheckOutView
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.fragment.BaseDialogFragment
import kotlinx.android.synthetic.main.checkout_dialog.view.*

class CheckOutDialog : BaseDialogFragment() {

    private lateinit var mPresenter: CheckoutPresenter

    lateinit var mcontext: CheckoutActivity

    companion object {

        private const val KEY_PrescriptionList = "KEY_PrescriptionList"
        private const val KEY_SHIPPING_ADDRESS = "address"
        private const val KEY_TOTAL = "total"
        lateinit var mContext: Context


        fun newInstance(
            context: Context,
            prescripitonList: String,
            shippingAddress: String,
            total_price: String
        ): CheckOutDialog {
            val args = Bundle()
            args.putString(KEY_PrescriptionList, prescripitonList)
            args.putString(KEY_SHIPPING_ADDRESS, shippingAddress)
            args.putString(KEY_TOTAL, total_price)

            val fragment = CheckOutDialog()

            fragment.arguments = args
            mContext = context
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.checkout_dialog, container, false)
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
            setCancelable(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    private fun setupView(view: View) {

        view.fulladdress.text = arguments?.getString(KEY_SHIPPING_ADDRESS)
        view.total_amounts.text = arguments?.getString(KEY_TOTAL)
        view.subtotal.text = arguments?.getString(KEY_TOTAL)

        view.prescription_rcy?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mPresenter = getPresenter<CheckoutPresenterImpl, CheckOutView>()
        var adapter = CheckoutAdpater(mPresenter)
        view.prescription_rcy?.adapter = adapter
        view.prescription_rcy?.setHasFixedSize(false)

        var data = arguments?.getString(KEY_PrescriptionList)
        val gson = Gson()
        var list = gson.fromJson(data, Array<PrescriptionVO>::class.java).toList()
        adapter.setNewData(list.toMutableList())

    }

    private fun setupClickListeners(view: View) {
        view.btn_payment.setOnClickListener {
            Toast.makeText(view.context, "ဆေးညွန်းမှာယူ ခြင်း အောင်မြင်ပါသည်", Toast.LENGTH_SHORT)
                .show()
            dismiss()
            startActivity(MainActivity.newIntent(mContext))
            mcontext.finish()

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mcontext = context as CheckoutActivity
    }

}