package com.padcx.mmz.myhealthcare.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.padcx.mmz.myhealthcare.R
import com.padcx.mmz.myhealthcare.fragments.AccountFragment
import com.padcx.mmz.myhealthcare.fragments.ConsultationFragment
import com.padcx.mmz.myhealthcare.fragments.SpecialityFragment
import com.padcx.mmz.shared.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(){

    companion object {
        fun newIntent(context: Context, startDestId: Int = R.id.action_speciality) : Intent {
            return Intent(context, MainActivity::class.java)
                .putExtra("startDest", startDestId)
        }

        fun startHistoryFragment(context: Context, startDestId: Int = R.id.action_chat): Intent {
            return newIntent(context, startDestId)
        }
    }

    private fun getStartDestId() = intent.getIntExtra("startDest", R.id.action_speciality)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("TOKEN", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = "token: $token"
                Log.e("TOKEN", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })*/

       /* FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Log.d("fbToken", it.token)
        }*/



        swapFragment(SpecialityFragment.newInstance(this))

        bottomNavigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.action_speciality -> {
                        swapFragment(SpecialityFragment.newInstance(this@MainActivity))
                        return true
                    }
                    R.id.action_chat -> {
                        swapFragment(ConsultationFragment.newInstance(this@MainActivity))
                        return true
                    }
                    R.id.action_account -> {
                        swapFragment(AccountFragment.newInstance(this@MainActivity))
                        return true
                    }
                }
                return false
            }

        })
    }

    private fun swapFragment(fragment: Fragment) {
        if(fragment!=null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.flBottomNavigationContainer, fragment)
                .commit()
        }

    }
}