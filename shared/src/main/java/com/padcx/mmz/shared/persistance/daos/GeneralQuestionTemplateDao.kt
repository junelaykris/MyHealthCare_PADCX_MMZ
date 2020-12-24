package com.padcx.mmz.shared.persistance.daos


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcx.mmz.shared.data.vos.GeneralQuestionTemplateVO

@Dao
interface GeneralQuestionTemplateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGeneralQuestionTemplate(generalQuestionTemplateVO: GeneralQuestionTemplateVO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGeneralQuestionTemplateList(generalQuestionList: List<GeneralQuestionTemplateVO>)

    @Query("select * from general_question_template")
    fun getAllGeneralQuestionTemplateData(): LiveData<List<GeneralQuestionTemplateVO>>

    @Query("select * from general_question_template WHERE id = :id")
    fun getAllGeneralQuestionTemplateBy(id: String): LiveData<GeneralQuestionTemplateVO>

    @Query("DELETE FROM general_question_template")
    fun deleteAllGeneralQuestionTemplate()

}