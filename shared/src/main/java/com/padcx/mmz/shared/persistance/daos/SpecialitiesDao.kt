package com.padcx.mmz.shared.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcx.mmz.shared.data.vos.DoctorVO
import com.padcx.mmz.shared.data.vos.SpecialitiesVO
import io.reactivex.Completable

/**
 * Created by Myint Myint Zaw on 11/27/2020.
 */
@Dao
interface SpecialitiesDao {

    @Query("SELECT * FROM specialities")
    fun getSpecialities() : LiveData<List<SpecialitiesVO>>

    @Query("SELECT * FROM specialities WHERE id = :specialtiesId")
    fun getSpecialitiesById(specialtiesId :String) : LiveData<SpecialitiesVO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpecialitiesList(detailList: List<SpecialitiesVO>)

    @Query("DELETE FROM specialities")
    fun deleteSpecialities()

}