package com.padcx.mmz.myhealthcare.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.activities.CaseSummaryActivity
import com.padcx.mmz.myhealthcare.activities.ConsultationChatRoomActivity
import com.padcx.mmz.myhealthcare.adapters.ConsultationAdapter
import com.padcx.mmz.myhealthcare.adapters.RecentDoctorAdapter
import com.padcx.mmz.myhealthcare.adapters.SpecialityAdapter
import com.padcx.mmz.myhealthcare.mvp.presenters.HomePresenter
import com.padcx.mmz.myhealthcare.mvp.presenters.impls.HomePresenterImpl
import com.padcx.mmz.myhealthcare.mvp.views.HomeView
import com.padcx.mmz.myhealthcare.utils.MyUserManager
import com.padcx.mmz.shared.data.vos.ConsultationRequestVO
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.RecentDoctorVO
import com.padcx.mmz.shared.data.vos.SpecialitiesVO
import com.padcx.mmz.shared.fragment.BaseFragment
import kotlinx.android.synthetic.main.speciality_confirm_dialog.view.*
import kotlinx.android.synthetic.main.speciality_fragment.*
import kotlinx.android.synthetic.main.speciality_fragment.view.*
import org.mmtextview.components.MMTextView

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class SpecialityFragment : BaseFragment(), HomeView {

    private lateinit var mPresenter: HomePresenter

    private lateinit var mSpecialityAdapter: SpecialityAdapter
    private lateinit var consultationAdapter: ConsultationAdapter
    private lateinit var mRecentDoctorAdapter: RecentDoctorAdapter


    companion object {
        lateinit var mContext: Context
        fun newInstance(context: Context): SpecialityFragment {
            mContext = context
            return SpecialityFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.speciality_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpRecyclerView()
        setUpActionListener()
    }

    private fun setUpRecyclerView() {
        rvSpeciality.layoutManager = GridLayoutManager(activity, 2)
        mSpecialityAdapter = SpecialityAdapter(mPresenter)
        rvSpeciality.adapter = mSpecialityAdapter

        rvRequestConsultation.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        consultationAdapter = ConsultationAdapter(mPresenter)
        rvRequestConsultation.adapter = consultationAdapter

        rvRecentDoctors.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        mRecentDoctorAdapter = RecentDoctorAdapter(mPresenter)
        rvRecentDoctors.adapter = mRecentDoctorAdapter

    }

    private fun setUpActionListener() {

    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<HomePresenterImpl, HomeView>()
        activity?.let { mPresenter.onUiReady(it, this) }
    }

    override fun showRecentDoctorList(recentDoctorList: List<RecentDoctorVO>) {
        if (recentDoctorList.isNotEmpty()) {
            rvRecentDoctors.visibility = View.VISIBLE
            txtRecent.visibility = View.VISIBLE
            mRecentDoctorAdapter.setNewData(recentDoctorList.toMutableList())
        } else {
            rvRecentDoctors.visibility = View.GONE
            txtRecent.visibility = View.GONE
        }
    }

    override fun showSpecialityList(list: List<SpecialitiesVO>) {
        mSpecialityAdapter.setNewData(list.toMutableList())
    }

    override fun showCaseSummaryPageDialog(specialitiesVO: SpecialitiesVO) {

        val view = layoutInflater.inflate(R.layout.speciality_confirm_dialog, null)
        val dialog = context?.let { Dialog(it) }
        val name = view?.findViewById<MMTextView>(R.id.speciality_name)
        name?.text =
            specialitiesVO?.name + resources.getString(R.string.consultation_confirm_message)

        dialog?.apply {
            setCancelable(false)
            setContentView(view)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        view.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        view.btnConfirm.setOnClickListener {
            var doctorVO = DoctorVO()
            var mDoctorVO = Gson().toJson(doctorVO)
            startActivity(
                CaseSummaryActivity.newIntent(
                    mContext,
                    specialitiesVO.id,
                    mDoctorVO.toString()
                )
            )
            dialog?.dismiss()
        }
        dialog?.show()

    }

    override fun nextPageToCaseSummaryFromRecentDoctor(doctorVO: RecentDoctorVO) {
        var doctorVO = DoctorVO(
            id = doctorVO.id.toString(),
            deviceId = doctorVO.deviceId.toString(),
            name = doctorVO.name.toString(),
            email = doctorVO.email.toString(),
            phone = doctorVO.phone.toString(),
            photo = doctorVO.photo.toString(),
            speciality = doctorVO.speciality.toString(),
            degree = doctorVO.degree.toString(),
            biography = doctorVO.biography.toString(),
            experience = doctorVO.experience.toString(),
            address = doctorVO.address.toString()
           /*
            specialityname = doctorVO.specialityname.toString(),
            dateOfBirth = doctorVO.dateOfBirth.toString(),
            gender = doctorVO.gender.toString(),*/



        )
        var mdoctorVO = Gson().toJson(doctorVO)
        startActivity(activity?.applicationContext?.let {
            CaseSummaryActivity.newIntent(
                it,
                doctorVO.speciality.toString(),
                mdoctorVO.toString()
            )
        })

    }

    override fun displayConsultationRequest(requestlist: List<ConsultationRequestVO>) {
        consultationAdapter.setNewData(requestlist.toMutableList())
    }

    override fun startChattingRoom(
        consultationID: String,
        consultationRequestVO: ConsultationRequestVO
    ) {
        activity?.let {
            mPresenter.statusUpdateForAcceptChat(it, consultationID, consultationRequestVO)
            it.startActivity(ConsultationChatRoomActivity.newIntent(it, consultationID))
        }
    }
}