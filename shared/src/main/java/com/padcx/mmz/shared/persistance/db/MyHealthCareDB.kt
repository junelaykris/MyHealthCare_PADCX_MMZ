package com.padcx.mmz.shared.persistance.db

import android.content.Context
import android.telephony.PreciseDataConnectionState
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.padcx.mmz.shared.data.vos.*
import com.padcx.mmz.shared.persistance.daos.*

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Database(entities =[SpecialitiesVO::class, PatientVO::class, DoctorVO::class,
        MedicineVO::class, ConsultationRequestVO::class, ConsultationChatVO::class,
        GeneralQuestionVO::class,SpecialQuestionVO::class,
        PrescriptionVO::class,ChatMessageVO::class,RecentDoctorVO::class,
        CheckoutVO::class,ConsultedPatientVO::class,GeneralQuestionTemplateVO::class], version = 9,exportSchema = false)
abstract class MyHealthCareDB: RoomDatabase() {
    companion object {
        val DB_NAME = "PADCX_HealthCare.DB"
        var dbInstance: MyHealthCareDB? = null

        fun getDBInstance(context: Context): MyHealthCareDB {
            when (dbInstance) {
                null -> {
                    dbInstance =
                        Room.databaseBuilder(context, MyHealthCareDB::class.java, DB_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }

            val i = dbInstance
            return i!!
        }
    }

    abstract fun doctorDao(): DoctorDao
    abstract fun patientDao(): PatientDao
    abstract fun specialitiesDao(): SpecialitiesDao
    abstract fun recentDoctorDao() : RecentDoctorDao
    abstract fun specialQuestionDao (): SpecialQuestionDao
    abstract fun consultationRequestDao(): ConsultationRequestDao
    abstract fun consultationChatDao(): ConsultationChatDao
    abstract fun chatMessageDao() : ChatMessageDao
    abstract fun consultedPatientDao () : ConsultedPatientDao
    abstract fun generalQuestionTemplateDao(): GeneralQuestionTemplateDao
    abstract fun medicineDao() : MedicineDao
    abstract fun prescriptionDao() : PrescriptionDao
}