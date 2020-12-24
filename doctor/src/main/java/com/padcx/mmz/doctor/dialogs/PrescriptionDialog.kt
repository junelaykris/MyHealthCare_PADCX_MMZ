package com.padcx.mmz.doctor.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcx.mmz.doctor.R
import com.padcx.mmz.doctor.adapters.PrescriptionInfoAdapter
import com.padcx.mmz.doctor.mvp.presenters.PrescriptionInfoPresenter
import com.padcx.mmz.doctor.mvp.presenters.impls.PrescriptionInfoPresenterImpl
import com.padcx.mmz.doctor.mvp.views.PrescriptionInfoView
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import com.padcx.mmz.shared.fragment.BaseDialogFragment
import kotlinx.android.synthetic.main.prescription_info_dialog.view.*

class PrescriptionDialog : BaseDialogFragment() , PrescriptionInfoView {

    private lateinit var mPresenter: PrescriptionInfoPresenter

    private lateinit var adapter: PrescriptionInfoAdapter

    companion object {

        private const val KEY_Chat_id = "KEY_Chat_id"
        private const val KEY_Patient_name ="Key_patient_name"
        private const val KEY_START_DATE ="KEY_START_DATE"

        fun newInstance(consultation_chat_id : String, patient_name : String, startDate : String): PrescriptionDialog {
            val args = Bundle()
            args.putString(KEY_Chat_id, consultation_chat_id)
            args.putString(KEY_Patient_name, patient_name)
            args.putString(KEY_START_DATE, startDate)
            val fragment = PrescriptionDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.prescription_info_dialog, container, false)
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

        var chat_id = arguments?.getString(KEY_Chat_id)
        var name = arguments?.getString(KEY_Patient_name)
        var sdate = arguments?.getString(KEY_START_DATE)

          view.pname.text  = name
          view.pstartdate.text = sdate

          view.rc_medicinelist?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

          mPresenter = getPresenter<PrescriptionInfoPresenterImpl, PrescriptionInfoView>()
          context?.let { mPresenter.onUiReady(it,this) }
          mPresenter.onUiReadyForPrescription(consulation_chat_id = chat_id.toString())
          adapter = PrescriptionInfoAdapter()
          view.rc_medicinelist?.adapter = adapter
    }

    private fun setupClickListeners(view: View) {
        view.btn_close.setOnClickListener {
            dismiss()
        }
    }

    override fun displayPrescriptionList(prescription_list: List<PrescriptionVO>) {
        adapter.setNewData(prescription_list.toMutableList())
    }

}