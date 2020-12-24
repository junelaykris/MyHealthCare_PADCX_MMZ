package com.padcx.mmz.shared.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcx.mmz.shared.data.vos.PatientVO

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPatientData(patientVO: PatientVO)

    @Query("select * from patient")
    fun getAllPatientData(): LiveData<List<PatientVO>>

    @Query("DELETE FROM patient")
    fun deleteAllPatientData()

    @Query("select * from patient WHERE email = :email")
    fun getAllPatientDataByEmail(email: String): LiveData<PatientVO>

}