package com.sneha.mynotes.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.sneha.mynotes.Interfaces.CheckListInterface
import com.sneha.mynotes.R
import com.sneha.mynotes.models.ChecklistMainModel

import androidx.recyclerview.widget.RecyclerView
import com.sneha.mynotes.CreateCheckListActivity

class CheckListMainAdapter// data is passed into the constructor
(internal var context: Context, private val arr: List<ChecklistMainModel>, internal var checkListInterface: CheckListInterface) : RecyclerView.Adapter<CheckListMainAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater

    init {
        this.mInflater = LayoutInflater.from(context)
    }

    // inflates the cell layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = mInflater.inflate(R.layout.view_checklist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(true)
        val obj = arr[position]

        holder.txtId.text = obj.id.toString() + ""
        holder.txtTitle.text = obj.checklistTitle
        holder.txtDate.text = obj.createdTime

        holder.btnDelete.setOnClickListener { checkListInterface.onCheckListDeleteClicked(arr[position]) }

        holder.txtTitle.setOnClickListener {
            var intent = Intent(context, CreateCheckListActivity::class.java)
            intent.putExtra("id", arr.get(position).id)
            context.startActivity(intent)
        }

    }

    // total number of cells
    override fun getItemCount(): Int {
        return arr.size
    }


    // stores and recycles views as they are scrolled off screen
    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtTitle: TextView
        internal var txtId: TextView
        internal var txtDate: TextView
        internal var btnDelete: ImageView

        init {
            txtTitle = itemView.findViewById<View>(R.id.txtCheckListName_CheckListItem) as TextView
            txtId = itemView.findViewById<View>(R.id.txtId_CheckListItem) as TextView
            txtDate = itemView.findViewById<View>(R.id.txtDate_CheckListItem) as TextView
            btnDelete = itemView.findViewById<View>(R.id.btnDelete_CheckListItem) as ImageView
        }

    }

}