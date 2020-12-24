package com.padcx.mmz.shared.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcx.mmz.shared.data.vos.RecentDoctorVO
import io.reactivex.Completable

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Dao
interface RecentDoctorDao {

    @Query("SELECT * FROM recently_doctor")
    fun getRecentDoctor(): LiveData<List<RecentDoctorVO>>

    @Query("SELECT * FROM recently_doctor WHERE id = :doctorId")
    fun getRecentDoctorById(doctorId: String): LiveData<RecentDoctorVO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecentDoctor(recentDoctor: RecentDoctorVO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentDoctorList(recentDoctorList: List<RecentDoctorVO>)

    @Query("select * from recently_doctor")
    fun getAllRecentDoctorData(): LiveData<List<RecentDoctorVO>>

    @Query("DELETE FROM recently_doctor")
    fun deleteAllRecentDoctorData()
}