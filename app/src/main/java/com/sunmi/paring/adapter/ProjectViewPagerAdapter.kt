package com.sunmi.paring.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.sunmi.paring.fragment.ProjectFragment


class ProjectViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){
    override fun getItem(pos: Int): Fragment = ProjectFragment.newInstance()

    override fun getCount(): Int = 5
}