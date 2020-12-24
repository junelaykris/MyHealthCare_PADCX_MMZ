package com.padcx.mmz.shared.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcx.mmz.shared.data.vos.ConsultedPatientVO

@Dao
interface ConsultedPatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConsultedPatient(patient: ConsultedPatientVO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConsultedPatient(patient: List<ConsultedPatientVO>)

    @Query("select * from consulted_patient WHERE doctor_id = :id")
    fun getConsultedPatient(id: String): LiveData<List<ConsultedPatientVO>>

    @Query("select * from consulted_patient WHERE id = :id")
    fun getConsultedPatientBy(id: String): LiveData<ConsultedPatientVO>

    @Query("DELETE FROM consulted_patient")
    fun deleteConsultedPatient()


}