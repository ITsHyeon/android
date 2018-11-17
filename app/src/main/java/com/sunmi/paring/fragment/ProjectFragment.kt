package com.sunmi.paring.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sunmi.paring.R
import com.sunmi.paring.activity.ProjectActivity
import com.sunmi.paring.model.Post
import com.sunmi.paring.util.DataHelper
import kotlinx.android.synthetic.main.fragment_project.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.SimpleDateFormat

class ProjectFragment : Fragment() {

    var post: Post = Post()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentRoot.onClick {
            (activity as ProjectActivity).startProjectInfo()
        }
        post = arguments!!.getSerializable("post") as Post
        Glide.with(this)
            .load("http://sunr.in:5100/" + post.thumbnail)
            .into(thumbNail)
        titleText.text = DataHelper.dataHelper!!.selectProject.title
        subTitleText.text = post.title
        dateText.text = SimpleDateFormat("yyyy.MM.dd").format(post.date)
        sponsorText.text = "후원자: " + post.sponsors.count().toString() + "명"
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProjectFragment()

        @JvmStatic
        fun newInstance(post: Post) = ProjectFragment().apply {
            arguments = Bundle().apply {
                putSerializable("post", post)
            }
        }

    }
}
