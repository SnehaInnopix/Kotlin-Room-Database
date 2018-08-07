package com.sneha.mynotes.Database

import com.sneha.mynotes.models.ChecklistMainModel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CheckListMainDao {

    @get:Query("SELECT * FROM ChecklistMainModel ORDER BY id ASC")
    val allChecklist: List<ChecklistMainModel>

    @Insert
    fun insertCheckListData(obj: ChecklistMainModel): Long

    @Update
    fun updateCheckList(obj: ChecklistMainModel)

    @Query("SELECT * FROM ChecklistMainModel WHERE id LIKE :id ")
    fun getAllChecklistObject(id: Int): ChecklistMainModel

    @Delete
    fun deleteCheckListMain(obj: ChecklistMainModel)


}
