package com.sunmi.paring.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.ImageViewCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.sunmi.paring.adapter.MainViewPagerAdapter
import com.sunmi.paring.R
import com.sunmi.paring.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBarColor(window, Color.WHITE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarIconColor(window, false)
        }
        setContentView(R.layout.activity_main)

        setViewPager()
    }

    var tabs = ArrayList<ImageView>()

    private fun setViewPager() {
        tabs.apply {
            add(tab1)
            add(tab2)
            add(tab3)
            add(tab4)
        }

        tabs.forEachIndexed { i, view ->
            view.setOnClickListener {
                tabSelected(i)
                viewPager.currentItem = i
            }
        }
        viewPager.run {
            adapter = MainViewPagerAdapter(supportFragmentManager)
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(p0: Int) {}
                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

                override fun onPageSelected(pos: Int) {
                    tabSelected(pos)
                }
            })
            tabSelected(0)
        }
    }

    private fun tabSelected(pos: Int) {
        tabs.forEachIndexed { i, imageView ->
            ImageViewCompat.setImageTintList(
                imageView, ColorStateList.valueOf(
                    if (i == pos)
                        ContextCompat.getColor(this, R.color.blue)
                    else
                        ContextCompat.getColor(this, R.color.gray)
                )
            )
        }
    }
}
