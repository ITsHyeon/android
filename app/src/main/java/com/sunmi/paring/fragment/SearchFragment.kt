package com.sunmi.paring.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.nitrico.lastadapter.LastAdapter
import com.sunmi.paring.BR
import com.sunmi.paring.R
import com.sunmi.paring.databinding.ItemProjectBinding
import com.sunmi.paring.model.Project
import kotlinx.android.synthetic.main.fragment_home.*

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    var items = ArrayList<Project>().apply {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()
    }


    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = LastAdapter(items, BR.item)
            .map<Project, ItemProjectBinding>(R.layout.item_project) {

            }
    }

    companion object {

        @JvmStatic
        fun newInstance() = SearchFragment()
    }

}
