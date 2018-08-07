package com.sneha.mynotes.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sneha.mynotes.models.ChecklistMainModel
import com.sneha.mynotes.models.CheckListItemModel


@Database(entities = arrayOf(ChecklistMainModel::class, CheckListItemModel::class), version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun checklistDao(): CheckListMainDao

    abstract fun checkListItemDao(): CheckListItemDao

    companion object {

        private var INSTANCE: MyDatabase? = null

        fun getAppDatabase(context: Context): MyDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "DailyNotes-database")
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as MyDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}