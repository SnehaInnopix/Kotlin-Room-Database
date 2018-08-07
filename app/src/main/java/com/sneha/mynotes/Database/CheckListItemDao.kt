package com.sneha.mynotes.Database


import com.sneha.mynotes.models.CheckListItemModel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CheckListItemDao {


    @get:Query("SELECT * FROM CheckListItemModel ORDER BY item_id ASC")
    val allChecklistItem: List<CheckListItemModel>

    @Insert
    fun insertItemData(obj: CheckListItemModel): Long

    @Update
    fun updateItem(obj: CheckListItemModel)

    @Query("SELECT * FROM CheckListItemModel WHERE checklist_id LIKE :id ORDER BY item_id ASC")
    fun getAllChecklistItemById(id: Int): List<CheckListItemModel>

    @Query("DELETE FROM CheckListItemModel WHERE checklist_id = :id")
    fun deleteCheckListItem(id: Int)


}
