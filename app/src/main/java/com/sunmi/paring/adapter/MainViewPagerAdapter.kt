package com.sunmi.paring.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.sunmi.paring.fragment.HomeFragment
import com.sunmi.paring.fragment.SearchFragment
import com.sunmi.paring.fragment.WriteFragment

class MainViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(pos: Int): Fragment = when (pos) {
        1 -> WriteFragment.newInstance()

        3 -> SearchFragment.newInstance()
        else -> HomeFragment.newInstance()
    }

    override fun getCount(): Int = 4
}
