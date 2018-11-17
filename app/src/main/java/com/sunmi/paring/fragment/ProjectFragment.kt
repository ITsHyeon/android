package com.sunmi.paring.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunmi.paring.R
import com.sunmi.paring.activity.ProjectActivity
import kotlinx.android.synthetic.main.fragment_project.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ProjectFragment : Fragment() {

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
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProjectFragment()
    }
}
