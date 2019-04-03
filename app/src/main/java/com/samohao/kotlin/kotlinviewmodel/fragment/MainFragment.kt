package com.samohao.kotlin.kotlinviewmodel.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import com.samohao.kotlin.kotlinviewmodel.R
import com.samohao.kotlin.kotlinviewmodel.activity.HomeActivity
import com.samohao.kotlin.kotlinviewmodel.viewModel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private val PAGE_HOME = 0
    private val PAGE_STORY = 1
    private val PAGE_CHAT = 2
    private val PAGE_MISSTION = 3

    private val adapter by lazy { HomePageAdapter(childFragmentManager) } //fragmentManager 사용하면 child Fragmnet생성전 추가됨
    private val arrFragment by lazy { arrayListOf(HomeFragment.newInstance() , StoryFragment.newInstance()
        , ChatFragment.newInstance() , MissionFragment.newInstance()) }

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

//        val home = HomeFragment()
//        home.fragmentInterfce = object: MainFragmentInterface {
//            override fun createdFragment() {
//                Handler().post {
//                    adapter.updatePage()
//                }
//            }
//        }
//        val story = StoryFragment()
//        val chat = ChatFragment()
//        val mission = MissionFragment()
//        arrFragment.add(home)
//        arrFragment.add(story)
//        arrFragment.add(chat)
//        arrFragment.add(mission)
        initLayout()
    }

    private fun initLayout() {
        ib_menu.setOnClickListener(View.OnClickListener {
            val mainActivity = activity
            if(mainActivity is HomeActivity) mainActivity.openDraw()
        })
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        viewpager.adapter = adapter
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                Toast.makeText(context , "page index is " + position , Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initTabLayout() {
        tabs.tabGravity = TabLayout.GRAVITY_FILL
        tabs.setupWithViewPager(viewpager)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                Toast.makeText(context , "tab reselect position" + tab?.position , Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
//                if(tab != null) {
//                    tabs.setScrollPosition(tab.position , 0f, false)
//                    viewpager.currentItem = tab.position
//                }
                Toast.makeText(context , "tab reselect position" + tab?.position , Toast.LENGTH_SHORT).show()
            }
        })
        updateTabTitle()
    }
    private fun updateTabTitle() {
        for(index in 0 until tabs.tabCount) {
            val tab = tabs.getTabAt(index) as TabLayout.Tab
            val v = LayoutInflater.from(context).inflate(R.layout.item_tab_main , null)
            val iv = v.findViewById(R.id.iv_title) as ImageView
            when(index) {
                PAGE_HOME -> iv.setImageResource(R.drawable.selector_main_tab_home)
                PAGE_STORY ->  iv.setImageResource(R.drawable.selector_main_tab_story)
                PAGE_CHAT ->  iv.setImageResource(R.drawable.selector_main_tab_chat)
                PAGE_MISSTION ->  iv.setImageResource(R.drawable.selector_main_tab_mission)
            }
            tab.customView = v
        }
    }

    private fun updateViewpager() {
        adapter?.notifyDataSetChanged()
    }

    private inner class HomePageAdapter(fm : FragmentManager?) : FragmentStatePagerAdapter(fm) {
        var items : List<Fragment> = arrayListOf()
        set(_) {
            notifyDataSetChanged()
        }

        override fun getItem(position: Int): Fragment {
            return arrFragment[position]
        }

        override fun getCount(): Int {
            return arrFragment.size
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }

    interface MainFragmentInterface {
        fun createdFragment()
    }

}
