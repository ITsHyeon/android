package com.sunmi.paring.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nitrico.lastadapter.LastAdapter
import com.sunmi.paring.BR
import com.sunmi.paring.R
import com.sunmi.paring.activity.ProjectActivity
import com.sunmi.paring.databinding.ItemProjectBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    var items = ArrayList<String>().apply {
        add("")
        add("")
        add("")
        add("")
        add("")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()
    }


    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = LastAdapter(items, BR.item)
            .map<String, ItemProjectBinding>(R.layout.item_project) {
                onClick {
                    startActivity(Intent(context, ProjectActivity::class.java))
                }
            }
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}
