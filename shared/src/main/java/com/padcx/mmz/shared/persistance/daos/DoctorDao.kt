package com.padcx.mmz.shared.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.PatientVO

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Dao
interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDoctorData(doctorVO: DoctorVO)

    @Query("SELECT * FROM doctor")
    fun getDoctor(): LiveData<List<DoctorVO>>

    @Query("DELETE FROM doctor")
    fun deleteAllDoctorData()

    @Query("SELECT * FROM doctor WHERE id = :doctorId")
    fun getDoctorById(doctorId: String): LiveData<DoctorVO>

    @Query("select * from doctor WHERE email = :email")
    fun getAllDoctorDataByEmail(email: String): LiveData<DoctorVO>

}