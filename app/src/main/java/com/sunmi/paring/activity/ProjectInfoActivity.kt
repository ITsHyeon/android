package com.sunmi.paring.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.github.nitrico.lastadapter.LastAdapter
import com.sunmi.paring.BR
import com.sunmi.paring.R
import com.sunmi.paring.adapter.GridItemDecoration
import com.sunmi.paring.databinding.ItemGridImageBinding
import com.sunmi.paring.util.DataHelper
import kotlinx.android.synthetic.main.activity_project_info.*
import org.jetbrains.anko.displayMetrics
import java.text.SimpleDateFormat

class ProjectInfoActivity : AppCompatActivity() {


    var post = DataHelper.dataHelper!!.selectPost
    lateinit var requestManager: RequestManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_info)

        requestManager = Glide.with(this)
        backButton.setOnClickListener {
            finish()
        }


        requestManager
            .load("http://sunr.in:5100/" + post.thumbnail)
            .into(thumbNail)

        dateText.text = SimpleDateFormat("yyyy.MM.dd").format(post.date)
        titleText.text = DataHelper.dataHelper!!.selectProject.title
        subTitleText.text = post.title
        sponsorText.text = "후원자: " + post.sponsors.count().toString() + "명"
        projectContent.text = post.desc

        recyclerView.run {
            layoutManager = GridLayoutManager(this@ProjectInfoActivity, 3)
            addItemDecoration(GridItemDecoration(3, makeDP(this@ProjectInfoActivity, 3f).toInt()))
            LastAdapter(post.images, BR.item)
                .map<String, ItemGridImageBinding>(R.layout.item_grid_image) {
                    onBind {
                        requestManager
                            .load("http://sunr.in:5100/" + it.binding.item)
                            .into(it.binding.imageView)
                    }
                }
                .into(this)
        }

//        ViewCompat.setTransitionName(dateText, "dateText")
//        ViewCompat.setTransitionName(titleText, "titleText")
//        ViewCompat.setTransitionName(thumbNail, "thumbNail")
//        ViewCompat.setTransitionName(sponsorText, "sponsorText")
//
//        window.exitTransition = null
    }

    fun makeDP(context: Context, dp: Float): Float = context.displayMetrics.density * dp

}
