package com.sneha.mynotes.Fragment


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.sneha.mynotes.R
import com.sneha.mynotes.adapters.CheckListMainAdapter
import com.sneha.mynotes.models.ChecklistMainModel
import kotlinx.android.synthetic.main.fragment_my_check_list.*
import android.content.Intent
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sneha.mynotes.CreateCheckListActivity
import com.sneha.mynotes.Database.MyDatabase
import com.sneha.mynotes.Interfaces.CheckListInterface


class MyCheckListFragment : Fragment(), View.OnClickListener, CheckListInterface {


    internal var view: View? = null

    lateinit var checkListMainAdapter: CheckListMainAdapter

    //lateinit var arr: List<ChecklistMainModel>
    var arr: MutableList<ChecklistMainModel> = ArrayList<ChecklistMainModel>()

    lateinit var mLayoutManager: RecyclerView.LayoutManager

    lateinit var database: MyDatabase

    companion object {

        fun newInstance(): MyCheckListFragment {
            return MyCheckListFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_my_check_list, container, false);


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getData()
        setData()
    }

    fun initViews() {
        btnAdd_CheckListFragment.setOnClickListener(this)

        database = MyDatabase.getAppDatabase(this!!.activity!!)

    }

    fun getData() {
        try {
            arr = database.checklistDao().allChecklist as MutableList<ChecklistMainModel>
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setData() {

        mLayoutManager = LinearLayoutManager(activity);

        checkListMainAdapter = CheckListMainAdapter(this?.getActivity()!!, arr, this);
        recyclerview_CheckListFragment.setLayoutManager(mLayoutManager);
        recyclerview_CheckListFragment.setAdapter(checkListMainAdapter);


    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnAdd_CheckListFragment -> {
                    //Toast.makeText(activity, "Button add clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, CreateCheckListActivity::class.java)
                    intent.putExtra("id", -1)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onCheckListDeleteClicked(obj: ChecklistMainModel?) {
        if (obj != null) {
            Toast.makeText(activity, "Delete clcicked " + obj.checklistTitle, Toast.LENGTH_SHORT).show()
            showDeleteDialog(obj)
        }
    }

    fun showDeleteDialog(obj: ChecklistMainModel) {

        var dialog: Dialog
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        var txtTitle = dialog.findViewById<TextView>(R.id.txtTitle_AlertDialog)
        var txtMsg = dialog.findViewById<TextView>(R.id.txtMsg_AlertDialog)
        var btnCancel = dialog.findViewById<TextView>(R.id.btnCancel_AlertDialog)
        var btnOk = dialog.findViewById<TextView>(R.id.btnOk_AlertDialog)


        txtTitle.setText("Alert")
        txtMsg.setText("Are you sure you want to delete '" + obj.checklistTitle + "' checklist ?")

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnOk.setOnClickListener {
            dialog.dismiss()
            arr.remove(obj)
            setData()
        }


        dialog.show()

        /*final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView txtTlt = (TextView) dialog.findViewById(R.id.txtTitle_AlertDialog);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMsg_AlertDialog);
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel_AlertDialog);
        TextView btnOk = (TextView) dialog.findViewById(R.id.btnOk_AlertDialog);

        txtTlt.setText("Alert");
        txtMessage.setText("Are you sure you want to delete '" + obj.getChecklistTitle() + "' checklist ?");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                database.checkListItemDao().deleteCheckListItem(obj.getId());
                database.checklistDao().deleteCheckListMain(obj);
                getData();
                setData();
            }

        });

        dialog.show();*/

    }

    override fun onResume() {
        super.onResume()
        getData()
        setData()
    }
}


/*
    View view;

    @BindView(R.id.btnAdd_CheckListFragment)
    FloatingActionButton btnAdd;

    @BindView(R.id.recyclerview_CheckListFragment)
    RecyclerView recyclerView;

    CheckListMainAdapter checkListMainAdapter;

    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<ChecklistMainModel> arr = new ArrayList<ChecklistMainModel>();

    MyDatabase database;

    LoaderDialog loaderDialog;


    public MyCheckListFragment() {
        // Required empty public constructor
    }

    public static MyCheckListFragment newInstance() {
        MyCheckListFragment fragment = new MyCheckListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_check_list, container, false);
        ButterKnife.bind(this, view);

        initView();
        loaderDialog.showLoaderDialog(true);
        getData();
        setData();
        loaderDialog.dismissLoaderDialog();
        return view;
    }

    private void initView() {

        database = MyDatabase.getAppDatabase(getActivity());

        btnAdd.setOnClickListener(this);

        loaderDialog = new LoaderDialog(getActivity());

    }

    private void getData() {
        try {
            arr = (ArrayList<ChecklistMainModel>) database.checklistDao().getAllChecklist();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {

        mLayoutManager = new LinearLayoutManager(getActivity());
        try {
            checkListMainAdapter = new CheckListMainAdapter(getActivity(),
                    arr, this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(checkListMainAdapter);
            *//*
            checkListMainAdapter.notifyDataSetChanged();*//*
        } catch (Exception e) {
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd_CheckListFragment:
                Intent intent = new Intent(getActivity(), CreateCheckListActivity.class);
                intent.putExtra("id", -1);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCheckListDeleteClicked(ChecklistMainModel obj) {
        showDeleteDialog(obj);
    }

    public void showDeleteDialog(final ChecklistMainModel obj) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView txtTlt = (TextView) dialog.findViewById(R.id.txtTitle_AlertDialog);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMsg_AlertDialog);
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel_AlertDialog);
        TextView btnOk = (TextView) dialog.findViewById(R.id.btnOk_AlertDialog);

        txtTlt.setText("Alert");
        txtMessage.setText("Are you sure you want to delete '" + obj.getChecklistTitle() + "' checklist ?");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                database.checkListItemDao().deleteCheckListItem(obj.getId());
                database.checklistDao().deleteCheckListMain(obj);
                getData();
                setData();
            }

        });

        dialog.show();

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setData();
    }*/
