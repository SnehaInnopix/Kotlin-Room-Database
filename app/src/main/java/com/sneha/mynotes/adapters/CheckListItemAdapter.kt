package com.sneha.mynotes.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import com.sneha.mynotes.Interfaces.CheckListItemInterface
import com.sneha.mynotes.R
import com.sneha.mynotes.models.CheckListItemModel

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class CheckListItemAdapter// data is passed into the constructor
(internal var context: Context, private val arr: MutableList<CheckListItemModel>, internal var checkListItemInterface: CheckListItemInterface) : RecyclerView.Adapter<CheckListItemAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater

    init {
        this.mInflater = LayoutInflater.from(context)
    }

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = mInflater.inflate(R.layout.view_checkitem_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(true)
        val obj = arr[position]

        holder.checkBox.text = obj.check_item + ""
        holder.txtDate.text = obj.updatedTime + ""
        holder.checkBox.isChecked = obj.isChecked

        holder.btnEdit.setOnClickListener { checkListItemInterface.editCheckListItem(arr[position]) }

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(context, "Checked : $isChecked", Toast.LENGTH_SHORT).show()
            checkListItemInterface.checkItemClicked(arr[position], isChecked, position)
        }


    }

    // total number of cells
    override fun getItemCount(): Int {
        return arr.size
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtDate: TextView
        internal var checkBox: CheckBox
        internal var btnEdit: ImageView

        var viewBackground: RelativeLayout
        var viewForeground: ConstraintLayout

        init {
            checkBox = itemView.findViewById<View>(R.id.checkBox_CheckListItem) as CheckBox
            btnEdit = itemView.findViewById<View>(R.id.btnEdit_CheckListItem) as ImageView
            txtDate = itemView.findViewById<View>(R.id.txtDate_CheckListItem) as TextView

            viewBackground = itemView.findViewById<View>(R.id.view_background_CheckListItem) as RelativeLayout
            viewForeground = itemView.findViewById<View>(R.id.view_foreground_CheckListItem) as ConstraintLayout
        }

    }


    fun removeItem(position: Int) {
        arr.removeAt(position)
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position)
    }

    fun restoreItem(item: CheckListItemModel, position: Int) {
        arr.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }


}