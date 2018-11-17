package com.sunmi.paring.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.github.nitrico.lastadapter.LastAdapter
import com.sunmi.paring.BR
import com.sunmi.paring.R
import com.sunmi.paring.activity.ProjectActivity
import com.sunmi.paring.databinding.ItemProjectBinding
import com.sunmi.paring.model.Project
import com.sunmi.paring.model.ProjectListWrapper
import com.sunmi.paring.util.DataHelper
import com.sunmi.paring.util.NetworkHelper
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {


    lateinit var requestManager: RequestManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    var items = ArrayList<Project>().apply {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requestManager = Glide.with(this)
        setRecyclerView()

        NetworkHelper.retrofitInstance.getProjectAll().enqueue(object : Callback<ProjectListWrapper> {
            override fun onFailure(call: Call<ProjectListWrapper>, t: Throwable) {
            }

            override fun onResponse(call: Call<ProjectListWrapper>, response: Response<ProjectListWrapper>) {
                DataHelper.dataHelper!!.projectList = response.body()?.data!!
                items.clear()
                items.addAll(DataHelper.dataHelper!!.projectList)
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        })
    }


    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = LastAdapter(items, BR.item)
            .map<Project, ItemProjectBinding>(R.layout.item_project) {
                onClick {
                    startActivity(Intent(context, ProjectActivity::class.java))
                    DataHelper.dataHelper?.selectProject = it.binding.item!!
                }

                onBind {
                    it.binding.author.text = "User:" + it.binding.item!!.author.name
                    it.binding.title.text = it.binding.item!!.title
                    requestManager
                        .load("http://sunr.in:5100/" + it.binding.item!!.posts[0].thumbnail)
                        .into(it.binding.thumbNail)
                }
            }
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}
