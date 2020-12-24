package com.padcx.mmz.myhealthcare.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.padcx.mmz.myhealthcare.utils.showCropImage
import com.padcx.mmz.shared.data.vos.PrescriptionVO
import kotlinx.android.synthetic.main.prescription_item_chat_viewpod.view.*

/**
 * Created by Myint Myint Zaw on 12/16/2020.
 */
class PrescriptionViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mDelegate: Delegate? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        setUpListener()

    }

    fun setPrescriptionData(
        prescription: List<PrescriptionVO>,
        doctorPhoto: String,
        chatId: String
    ) {
        ivUser.showCropImage(doctorPhoto)
        var str: String = ""
        if (prescription.isNotEmpty()) {
            for (item in prescription) {
                str += item.medicine_name + "\n"
            }
        }
        txtMedicineName.text = str.toString()

        btnPrescription.setOnClickListener {
            mDelegate?.onTapPrescription(chatId)
        }
    }

    fun setDelegate(delegate: Delegate) {
        mDelegate = delegate
    }

    private fun setUpListener() {

    }

    interface Delegate {
        fun onTapPrescription(chatId: String)
    }

}