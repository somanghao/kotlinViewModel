package com.samohao.kotlin.kotlinviewmodel.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.fragment.DrawerFragment
import com.samohao.kotlin.kotlinviewmodel.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

    const val PAGE_DRAWER = 0
    const val PAGE_HOME = 1

class HomeActivity : AppCompatActivity() {

    private val adapter by lazy { DrawerPagerAdatper(supportFragmentManager) }
    private val mItems by lazy { arrayListOf(DrawerFragment.newInstance() , HomeFragment.newInstance()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        initUI()
    }

    private fun initUI() {
        viewpager.adapter = adapter
        viewpager.setCurrentItem(PAGE_HOME, true)
    }

    override fun onBackPressed() {
        if (viewpager.currentItem == PAGE_DRAWER) {
            viewpager.setCurrentItem(PAGE_HOME , true)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private inner class DrawerPagerAdatper(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getCount(): Int {
            return mItems.size
        }

        override fun getItem(position: Int): Fragment {
            return mItems[position]
        }
    }
}
