package com.samohao.kotlin.kotlinviewmodel.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.feature.drawer.DrawerFragment
import com.samohao.kotlin.kotlinviewmodel.feature.maintab.MainFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

    const val PAGE_DRAWER = 0
    const val PAGE_HOME = 1

class HomeActivity : AppCompatActivity() {

    private val adapter by lazy { DrawerPagerAdatper(supportFragmentManager) }
    private val arrFragment by lazy { arrayListOf(DrawerFragment.newInstance() , MainFragment.newInstance()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT < 16) {
//            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        } else {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//            actionBar?.hide()
//        }

        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        initUI()
    }

    private fun initUI() {
        viewpager.adapter = adapter
        viewpager.setCurrentItem(PAGE_HOME, true)
    }

    fun openDraw() {
        viewpager.currentItem = PAGE_DRAWER
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
            return arrFragment.size
        }

        override fun getItem(position: Int): Fragment {
            return arrFragment[position]
        }
    }
}
