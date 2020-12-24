package com.padcx.mmz.shared.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcx.mmz.shared.data.vos.PrescriptionVO


@Dao
interface PrescriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrescription(data: PrescriptionVO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrescriptionList(list: List<PrescriptionVO>)

    @Query("select * from prescription")
    fun getAllPrescriptionData(): LiveData<List<PrescriptionVO>>

    @Query("select * from prescription WHERE chat_id = :chatId")
    fun getAllPrescriptionData(chatId: String): LiveData<List<PrescriptionVO>>


    @Query("DELETE FROM prescription")
    fun deleteAllPrescriptionData()

    @Query("DELETE FROM prescription WHERE id = :id")
    fun deletePrescriptionById(id: String)
}