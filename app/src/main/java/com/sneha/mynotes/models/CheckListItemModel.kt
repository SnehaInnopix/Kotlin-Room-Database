package com.sneha.mynotes.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CheckListItemModel")
class CheckListItemModel : Parcelable {

    @ColumnInfo(name = "checklist_id")
    var checklist_id: Int = 0

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    var item_id: Int = 0

    @ColumnInfo(name = "check_item")
    lateinit var check_item: String

    @ColumnInfo(name = "isChecked")
    var isChecked = false

    @ColumnInfo(name = "important")
    var important: Int = 0
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

    constructor(checklist_id: Int, item_id: Int, check_item: String, isChecked: Boolean, important: Int, createdTime: String, updatedTime: String) {
        this.checklist_id = checklist_id
        this.item_id = item_id
        this.check_item = check_item
        this.isChecked = isChecked
        this.important = important
        this.createdTime = createdTime
        this.updatedTime = updatedTime
    }

    protected constructor(`in`: Parcel) {
        checklist_id = `in`.readInt()
        item_id = `in`.readInt()
        check_item = `in`.readString()
        isChecked = `in`.readByte().toInt() != 0
        important = `in`.readInt()
        createdTime = `in`.readString()
        updatedTime = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(checklist_id)
        dest.writeInt(item_id)
        dest.writeString(check_item)
        dest.writeByte((if (isChecked) 1 else 0).toByte())
        dest.writeInt(important)
        dest.writeString(createdTime)
        dest.writeString(updatedTime)
    }

    companion object CREATOR : Parcelable.Creator<CheckListItemModel> {
        override fun createFromParcel(parcel: Parcel): CheckListItemModel {
            return CheckListItemModel(parcel)
        }

        override fun newArray(size: Int): Array<CheckListItemModel?> {
            return arrayOfNulls(size)
        }
    }

}
