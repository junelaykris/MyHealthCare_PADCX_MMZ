package com.padcx.mmz.myhealthcare.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.fragments.GeneralQuestionFragment
import com.padcx.mmz.myhealthcare.fragments.SpecialQuestionFragment
import com.padcx.mmz.myhealthcare.utils.MyUserManager
import com.padcx.mmz.shared.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_casesummary.*

/**
 * Created by Myint Myint Zaw on 12/11/2020.
 */
class CaseSummaryActivity : BaseActivity() {

    lateinit var swapfragment: Fragment
    var currentPosition: String = "0"
    var oldPatient: Int? = 0
    private var speciality: String? = ""
    private var doctorVO: String? = ""
    private var email: String = ""

    companion object {
        const val PARAM_ID = "Speciality ID"
        const val PARAM_DOCTOR = "PARAM DOCTOR"
        fun newIntent(context: Context, documentId: String, doctorID: String): Intent {
            val intent = Intent(context, CaseSummaryActivity::class.java)
            intent.putExtra(PARAM_ID, documentId)
            intent.putExtra(PARAM_DOCTOR, doctorID)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_casesummary)

        speciality = intent.getStringExtra(PARAM_ID)
        doctorVO = intent.getStringExtra(PARAM_DOCTOR)
        email = MyUserManager.getUser().email.toString()

        swapCurrentFragment("0")

        /*  if (oldPatient == 0) {
              swapCurrentFragment("0")
          } else {
              swapCurrentFragment("1")
          }
  */
        toolbarLayout.setNavigationOnClickListener {
            when (currentPosition) {
                "0" -> {
                    finish()
                }
                else -> {
                    finish()
                }
            }
        }
        toolBarStyle()
        toolbarLayout.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun toolBarStyle() {
        setSupportActionBar(toolbarLayout)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    fun swapCurrentFragment(currentPage: String) {
        when (currentPage) {
            "0" -> {
                swapfragment = GeneralQuestionFragment.newInstance(this)
                view_one.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_400))
                tv_general.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_bg_blue_color))
                tv_general.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_check_24
                    )
                )

                tv_special_question.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_bg_color))
                tv_special_question.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_check_24
                    )
                )

            }
            else -> {
                swapfragment = SpecialQuestionFragment.newInstance(this, speciality, doctorVO)
                view_one.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))

                tv_general.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_bg_blue_color))
                tv_general.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_check_24
                    )
                )

                tv_special_question.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_bg_blue_color))
                tv_special_question.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_baseline_check_24
                    )
                )

            }
        }
        currentPosition = currentPage

        val supportFragmentManager =
            supportFragmentManager.beginTransaction().replace(R.id.fl_question, swapfragment)
        supportFragmentManager.commit()
    }
}