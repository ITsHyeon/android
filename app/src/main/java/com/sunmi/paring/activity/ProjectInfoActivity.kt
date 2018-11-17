package com.sunmi.paring.activity

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import com.sunmi.paring.R
import kotlinx.android.synthetic.main.activity_project_info.*

class ProjectInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_info)

        backButton.setOnClickListener {
            finish()
        }
//        ViewCompat.setTransitionName(dateText, "dateText")
//        ViewCompat.setTransitionName(titleText, "titleText")
//        ViewCompat.setTransitionName(thumbNail, "thumbNail")
//        ViewCompat.setTransitionName(sponsorText, "sponsorText")
//
//        window.exitTransition = null
    }
}
