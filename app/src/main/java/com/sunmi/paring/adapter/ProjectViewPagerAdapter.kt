package com.sunmi.paring.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.sunmi.paring.fragment.ProjectFragment
import com.sunmi.paring.model.Post


class ProjectViewPagerAdapter(fm: FragmentManager, var items: ArrayList<Post>) : FragmentPagerAdapter(fm) {
    override fun getItem(pos: Int): Fragment = ProjectFragment.newInstance(items[pos])

    override fun getCount(): Int = items.size
}
