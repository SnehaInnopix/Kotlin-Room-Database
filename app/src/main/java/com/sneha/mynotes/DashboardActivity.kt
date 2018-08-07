package com.sneha.mynotes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.sneha.mynotes.R.menu.navigation
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.toolbar.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sneha.mynotes.Fragment.MyCheckListFragment
import com.sneha.mynotes.Fragment.MyDiaryListFragment


class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initView()
    }


    fun initView() {
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        navigation_DashboardActivity.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        pushFragment(MyCheckListFragment.newInstance())
        txtHeading_Toolbar.setText(R.string.dash_tlt_my_checklist)
    }


    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.getItemId()) {
                R.id.navigation_mydiary -> {
                    pushFragment(MyDiaryListFragment.newInstance())
                    txtHeading_Toolbar.setText(R.string.dash_tlt_my_diary)
                }
                R.id.navigation_mychecklist -> {
                    pushFragment(MyCheckListFragment.newInstance())
                    txtHeading_Toolbar.setText(R.string.dash_tlt_my_checklist)
                }
            }
            return true
        }

    }

    fun pushFragment(fragment: Fragment?) {
        if (fragment == null)
            return

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.framelayout_DashboardActivity, fragment, "OrderListFragment")
                .commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_dashboard, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Settings clicked...", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_profile -> {
                Toast.makeText(this, "Profile clicked...", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_logout -> {
                Toast.makeText(this, "Logout clicked...", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
