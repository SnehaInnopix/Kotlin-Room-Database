package com.sneha.mynotes

import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.sneha.mynotes.Database.MyDatabase
import com.sneha.mynotes.Util.Util
import com.sneha.mynotes.adapters.CheckListItemAdapter
import com.sneha.mynotes.models.CheckListItemModel
import com.sneha.mynotes.models.ChecklistMainModel
import kotlinx.android.synthetic.main.activity_create_check_list.*
import kotlinx.android.synthetic.main.toolbar.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sneha.mynotes.Interfaces.CheckListItemInterface
import com.sneha.mynotes.Util.RecyclerItemTouchHelper


class CreateCheckListActivity : AppCompatActivity(), View.OnClickListener, CheckListItemInterface, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    var objChecklistMainModel = ChecklistMainModel()

    var checkListItemAdapter: CheckListItemAdapter? = null
    var arrItems: ArrayList<CheckListItemModel> = ArrayList()

    var selectedSeverity = 2
    var itemSelectedSeverity = 2

    var mLayoutManager: RecyclerView.LayoutManager? = null

    lateinit var database: MyDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_check_list)

        initViews()

    }

    fun initViews() {
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        txtHeading_Toolbar.setText(getString(R.string.act_tlt_create_checklist))

        database = MyDatabase.getAppDatabase(this);


        btnEdit_CreateCheckListActivity.setOnClickListener(this)
        btnAddItem_CreateCheckListActivity.setOnClickListener(this)

        var id: Int = getIntent().getIntExtra("id", -1);
        if (id == -1) {
            showCreateCheckListDialog("Create CheckList", false);
        } else {
            getCheckListMainModel(id);
            getCheckListItem();
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnEdit_CreateCheckListActivity -> {
                    Toast.makeText(this, "Button Edit clicked", Toast.LENGTH_SHORT).show()
                    showCreateCheckListDialog("Edit CheckList", true);
                }
                R.id.btnAddItem_CreateCheckListActivity -> {
                    Toast.makeText(this, "Button add Item clicked", Toast.LENGTH_SHORT).show()
                    showCreateItemDialog("Create CheckList Item", false, CheckListItemModel());
                }
            }
        }
    }

    fun showCreateCheckListDialog(tlt: String, isEdit: Boolean) {
        var dialog: Dialog = Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_create_checklist_main);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        var txtTlt = dialog.findViewById<TextView>(R.id.txtTitle_CreateCheckListDialog)
        var edtCheckList = dialog.findViewById<EditText>(R.id.edtTitle_CreateCheckListDialog)
        var btnCancel = dialog.findViewById<TextView>(R.id.btnCancel_CreateCheckListDialog);
        var btnOk = dialog.findViewById<TextView>(R.id.btnOk_CreateCheckListDialog);
        var radioGroup = dialog.findViewById<RadioGroup>(R.id.rdGroup_CreateCheckListDialog);

        txtTlt.setText("" + tlt);

        if (isEdit) {
            selectedSeverity = objChecklistMainModel.important;
            when (selectedSeverity) {
                1 ->
                    radioGroup.check(R.id.rdBtnLow_CreateCheckListDialog);
                2 ->
                    radioGroup.check(R.id.rdBtnMedium_CreateCheckListDialog);
                3 ->
                    radioGroup.check(R.id.rdBtnHigh_CreateCheckListDialog);
            }
            edtCheckList.setText(objChecklistMainModel.checklistTitle);
        } else {
            selectedSeverity = 2;
        }

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener() { radioGroup: RadioGroup, i: Int ->

            var rb = radioGroup.findViewById<RadioButton>(i);
            Toast.makeText(this, "Checked : " + rb.text, Toast.LENGTH_SHORT).show()
            when (i) {
                R.id.rdBtnLow_CreateCheckListDialog ->
                    selectedSeverity = 1;
                R.id.rdBtnMedium_CreateCheckListDialog ->
                    selectedSeverity = 2;
                R.id.rdBtnHigh_CreateCheckListDialog ->
                    selectedSeverity = 3;
            }
        })

        btnCancel.setOnClickListener {
            dialog.dismiss()
            if (!isEdit) {
                onBackPressed();
            }
        }

        btnOk.setOnClickListener(View.OnClickListener {
            if (edtCheckList.getText().toString().length <= 0) {
                edtCheckList.setError("Please Enter Checklist title");
            } else {
                dialog.dismiss();

                // loaderDialog.showLoaderDialog(true);

                objChecklistMainModel.checklistTitle = edtCheckList.getText().toString() + "";
                objChecklistMainModel.important = selectedSeverity;

                if (isEdit) {
                    objChecklistMainModel.updatedTime = Util.currentTime
                } else {
                    objChecklistMainModel.createdTime = Util.currentTime
                    objChecklistMainModel.updatedTime = Util.currentTime
                }

                txtCheckListTitle_CreateCheckListActivity.setText(objChecklistMainModel.checklistTitle + "");
                txtDate_CreateCheckListActivity.setText(objChecklistMainModel.createdTime);

                if (isEdit) {
                    updateCheckList();
                } else {
                    createCheckList();
                }
            }

        })

        dialog.show();

    }

    fun createCheckList() {
        var id: Long = database.checklistDao().insertCheckListData(objChecklistMainModel);
        objChecklistMainModel.id = id.toInt()

        println("DB created id : " + id)


    }

    fun updateCheckList() {
        database.checklistDao().updateCheckList(objChecklistMainModel);
    }

    /**
     * fun : getCheckListMainModel
     * This function is use for getting already presented CheckList Main Data by its 'id' from database
     */
    fun getCheckListMainModel(id: Int) {
        objChecklistMainModel = database.checklistDao().getAllChecklistObject(id);
        setData();
    }

    /**
     * fun : setData
     * This function is use for setting already presented CheckList Main Data
     */
    fun setData() {
        txtCheckListTitle_CreateCheckListActivity.setText(objChecklistMainModel.checklistTitle);
        txtDate_CreateCheckListActivity.setText(objChecklistMainModel.updatedTime);
    }

    fun showCreateItemDialog(tlt: String, isEdit: Boolean, objItem: CheckListItemModel) {
        var dialog: Dialog = Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_create_checklist_main);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        var txtTlt = dialog.findViewById<TextView>(R.id.txtTitle_CreateCheckListDialog)
        var edtCheckList = dialog.findViewById<EditText>(R.id.edtTitle_CreateCheckListDialog)
        var btnCancel = dialog.findViewById<TextView>(R.id.btnCancel_CreateCheckListDialog);
        var btnOk = dialog.findViewById<TextView>(R.id.btnOk_CreateCheckListDialog);
        var radioGroup = dialog.findViewById<RadioGroup>(R.id.rdGroup_CreateCheckListDialog);

        txtTlt.setText("" + tlt);

        if (isEdit) {
            itemSelectedSeverity = objItem.important;
            when (itemSelectedSeverity) {
                1 ->
                    radioGroup.check(R.id.rdBtnLow_CreateCheckListDialog);
                2 ->
                    radioGroup.check(R.id.rdBtnMedium_CreateCheckListDialog);
                3 ->
                    radioGroup.check(R.id.rdBtnHigh_CreateCheckListDialog);
            }
            edtCheckList.setText(objItem.check_item);
        } else {
            itemSelectedSeverity = 2;
        }

        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener() { radioGroup: RadioGroup, i: Int ->

            var rb = radioGroup.findViewById<RadioButton>(i);
            Toast.makeText(this, "Checked : " + rb.text, Toast.LENGTH_SHORT).show()
            when (i) {
                R.id.rdBtnLow_CreateCheckListDialog ->
                    itemSelectedSeverity = 1;
                R.id.rdBtnMedium_CreateCheckListDialog ->
                    itemSelectedSeverity = 2;
                R.id.rdBtnHigh_CreateCheckListDialog ->
                    itemSelectedSeverity = 3;
            }
        })

        btnCancel.setOnClickListener {
            dialog.dismiss()
            //  if (!isEdit) {
            //      onBackPressed();
            //  }
        }

        btnOk.setOnClickListener(View.OnClickListener {
            if (edtCheckList.getText().toString().length <= 0) {
                edtCheckList.setError("Please Enter Checklist title");
            } else {
                dialog.dismiss();

                // loaderDialog.showLoaderDialog(true);

                objItem.checklist_id = objChecklistMainModel.id
                objItem.check_item = edtCheckList.getText().toString() + "";
                objItem.important = itemSelectedSeverity;

                if (isEdit) {
                    objItem.updatedTime = Util.currentTime
                } else {
                    objItem.createdTime = Util.currentTime
                    objItem.updatedTime = Util.currentTime
                    objItem.isChecked = false
                }

                if (isEdit) {
                    updateItem(objItem);
                } else {
                    createItem(objItem);
                }

                //loaderDialog.dismissLoaderDialog();
            }

        })

        dialog.show();
    }


    fun createItem(objItem: CheckListItemModel) {
        database.checkListItemDao().insertItemData(objItem);
        arrItems.clear();
        getCheckListItem();
    }

    fun updateItem(objItem: CheckListItemModel) {
        database.checkListItemDao().updateItem(objItem);
        arrItems.clear();
        getCheckListItem();
    }

    fun getCheckListItem() {
        arrItems = database.checkListItemDao().getAllChecklistItemById(objChecklistMainModel.id) as ArrayList<CheckListItemModel>;

        setListData();
    }

    fun setListData() {

        val mLayoutManager: RecyclerView.LayoutManager

        mLayoutManager = LinearLayoutManager(this@CreateCheckListActivity)
        try {
            checkListItemAdapter = CheckListItemAdapter(this@CreateCheckListActivity,
                    arrItems, this)
            recyclerview_CreateCheckListActivity.setLayoutManager(mLayoutManager)
            recyclerview_CreateCheckListActivity.setAdapter(checkListItemAdapter)


            // adding item touch helper
            // only ItemTouchHelper.LEFT added to detect Right to Left swipe
            // if you want both Right -> Left and Left -> Right
            // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
            val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerview_CreateCheckListActivity)

        } catch (e: Exception) {
        }

    }

    override fun editCheckListItem(objItem: CheckListItemModel?) {
        if (objItem != null) {
            showCreateItemDialog("Edit CheckList Item", true, objItem)
        };
    }

    override fun checkItemClicked(objItem: CheckListItemModel?, isChecked: Boolean, pos: Int) {
        //loaderDialog.showLoaderDialog(true);
        if (objItem != null) {
            arrItems.get(pos).isChecked = isChecked
            database.checkListItemDao().updateItem(objItem)
        }
        //loaderDialog.dismissLoaderDialog();
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is CheckListItemAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            var name: String = arrItems.get(viewHolder.getAdapterPosition()).check_item;

            // backup of removed item for undo purpose
            var deletedItem: CheckListItemModel = arrItems.get(viewHolder.getAdapterPosition());
            var deletedIndex: Int = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            checkListItemAdapter!!.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            var snackbar: Snackbar = Snackbar
                    .make(constraintLayout, name + " removed from CheckList!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", View.OnClickListener() {
                checkListItemAdapter!!.restoreItem(deletedItem, deletedIndex);
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem?): Boolean {
        if (menuItem != null) {
            if (menuItem.itemId == android.R.id.home) {
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }


}

/*


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddItem_CreateCheckListActivity:
                //Toast.makeText(this, "Add Item Clcik", Toast.LENGTH_SHORT).show();
                showCreateItemDialog("Create CheckList Item", false, new CheckListItemModel());
                break;
            case R.id.btnEdit_CreateCheckListActivity:
                //Toast.makeText(this, "Edit click", Toast.LENGTH_SHORT).show();
                showCreateCheckListDialog("Edit CheckList", true);
                break;
        }
    }


 */
