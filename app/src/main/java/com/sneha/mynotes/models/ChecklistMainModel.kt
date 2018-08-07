package com.sneha.mynotes.models

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.util.ArrayList

@SuppressLint("ParcelCreator")
@Entity(tableName = "ChecklistMainModel")
class ChecklistMainModel : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "checklistTitle")
    lateinit var checklistTitle: String

    @ColumnInfo(name = "important")
    var important: Int = 1
    /**
     * 1 = low (green)
     * 2 = medium (orange)
     * 3 = high (red)
     */

    @ColumnInfo(name = "createdTime")
    lateinit var createdTime: String

    @ColumnInfo(name = "updatedTime")
    lateinit var updatedTime: String

    constructor() {}

    constructor(id: Int, checklistTitle: String, important: Int, createdTime: String, updatedTime: String) {
        this.id = id
        this.checklistTitle = checklistTitle
        this.important = important
        this.createdTime = createdTime
        this.updatedTime = updatedTime
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readInt()
        checklistTitle = `in`.readString()
        important = `in`.readInt()
        createdTime = `in`.readString()
        updatedTime = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(checklistTitle)
        dest.writeInt(important)
        dest.writeString(createdTime)
        dest.writeString(updatedTime)
    }

    companion object CREATOR : Parcelable.Creator<ChecklistMainModel> {
        override fun createFromParcel(parcel: Parcel): ChecklistMainModel {
            return ChecklistMainModel(parcel)
        }

        override fun newArray(size: Int): Array<ChecklistMainModel?> {
            return arrayOfNulls(size)
        }
    }

}
