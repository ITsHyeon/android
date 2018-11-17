package com.sunmi.paring.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.github.nitrico.lastadapter.LastAdapter
import com.sunmi.paring.BR
import com.sunmi.paring.EndDrawerToggle
import com.sunmi.paring.R
import com.sunmi.paring.adapter.ProjectViewPagerAdapter
import com.sunmi.paring.databinding.ItemSponsorBinding
import com.sunmi.paring.fragment.ProjectFragment
import com.sunmi.paring.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.fragment_project.view.*
import kotlinx.android.synthetic.main.layout_project.*
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.image
import org.jetbrains.anko.support.v4.onPageChangeListener
import android.support.v4.util.Pair as UtilPair

class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBarColor(window, Color.WHITE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarIconColor(window, false)
        }
        setContentView(R.layout.activity_project)

        initToolbar()
        initNavigationDrawer()
        initViewPager()
        initRecyclerView()

        backButton.setOnClickListener {
            finish()
        }
    }

    fun makeDP(context: Context, dp: Float): Float = context.displayMetrics.density * dp

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        toolbar.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                val size = Point()
                windowManager.defaultDisplay.getSize(size)
                val screenWidth = size.x
                val arr = IntArray(2)
                titleText.getLocationOnScreen(arr)
                val textX = arr[0]
                titleText.width = (screenWidth - textX - titleText.width + makeDP(this@ProjectActivity, 40f)).toInt()
                toolbar.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun initNavigationDrawer() {
        val drawerToggle = EndDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun initViewPager() {
        viewPager.run {
            adapter = ProjectViewPagerAdapter(supportFragmentManager)
            pageMargin = makeDP(this@ProjectActivity, 12f).toInt()

            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val page = (viewPager.adapter!!.instantiateItem(viewPager, 0)) as ProjectFragment
                    currentFragment = page.view!!
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
            onPageChangeListener {
                onPageSelected {
                    val page = (viewPager.adapter!!.instantiateItem(viewPager, it)) as ProjectFragment
                    currentFragment = page.view!!
                }
            }
        }
    }

    var items = ArrayList<String>().apply {
        add("")
        add("")
        add("")
    }

    lateinit var currentFragment: View
    fun startProjectInfo() {
//        projectContainer.layoutParams = FrameLayout.LayoutParams(
//            currentFragment.fragmentContainer.width,
//            currentFragment.fragmentContainer.height
//        ).apply {
//            val arr = IntArray(2)
//            currentFragment.fragmentRoot.getLocationOnScreen(arr)
//            val x = arr[0]
//            val y = arr[1]
//            topMargin = (y - makeDP(this@ProjectActivity, 24f)).toInt()
//            leftMargin = x
//        }
//
//        dateText.text = currentFragment.dateText.text
//        titleText2.text = currentFragment.titleText.text
//        thumbNail.image = currentFragment.thumbNail.image
//        sponsorText.text = currentFragment.sponsorText.text

//        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this,
//            UtilPair<View, String>(dateText, "dateText"),
//            UtilPair<View, String>(titleText2, "titleText"),
//            UtilPair<View, String>(thumbNail, "thumbNail"),
//            UtilPair<View, String>(sponsorText, "sponsorText")
//        )
//        ActivityCompat.startActivity(this, Intent(this, ProjectInfoActivity::class.java), activityOptions.toBundle());
        startActivity(Intent(this, ProjectInfoActivity::class.java))
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        LastAdapter(items, BR.item)
            .map<String, ItemSponsorBinding>(R.layout.item_sponsor) {

            }
            .into(recyclerView)
    }
}
