package com.padcx.mmz.shared.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcx.mmz.shared.data.vos.MedicineVO


@Dao
interface MedicineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicalData(data: MedicineVO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedicalDataList(list: List<MedicineVO>)

    @Query("select * from medicine")
    fun getAllMedicine(): LiveData<List<MedicineVO>>

    @Query("select * from medicine WHERE id = :id")
    fun getAllMedicineByData(id: String): LiveData<MedicineVO>

    @Query("DELETE FROM medicine")
    fun deleteAllMedicine()

}