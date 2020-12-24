package com.padcx.mmz.shared.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcx.mmz.shared.data.vos.SpecialQuestionVO

/**
 * Created by Myint Myint Zaw on 12/14/2020.
 */
@Dao
interface SpecialQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpecialQuestions(special_questions: SpecialQuestionVO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpecialQuestions(special_questions: List<SpecialQuestionVO>)

    @Query("select * from special_question")
    fun getAllSpecialQuestionsData(): LiveData<List<SpecialQuestionVO>>

    @Query("select * from special_question WHERE id = :id")
    fun getAllSpecialQuestionsBy(id: String): LiveData<SpecialQuestionVO>

    @Query("DELETE FROM special_question")
    fun deleteSpecialQuestions()

}