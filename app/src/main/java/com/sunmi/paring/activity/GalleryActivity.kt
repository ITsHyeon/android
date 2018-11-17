package com.sunmi.paring.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.github.nitrico.lastadapter.LastAdapter
import com.sunmi.paring.BR
import com.sunmi.paring.adapter.GridItemDecoration
import com.sunmi.paring.R
import com.sunmi.paring.databinding.ItemGalleryBinding
import com.sunmi.paring.util.ImageUtils
import kotlinx.android.synthetic.main.activity_gallery.*
import org.jetbrains.anko.displayMetrics

class GalleryActivity : AppCompatActivity() {

    private val items = ArrayList<String>()
    private val selectedArray = ArrayList<Int>()

    var max = 30
    lateinit var requestManager: RequestManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        requestManager = Glide.with(this)
        setToolbar()
        setRecyclerView()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun setRecyclerView() {
        items.addAll(ImageUtils.getPathOfAllImages(this))
        galleyRecyclerView.run {
            layoutManager = GridLayoutManager(this@GalleryActivity, 3)
            LastAdapter(items, BR.item)
                .map<String, ItemGalleryBinding>(R.layout.item_gallery) {
                    onBind {
                        it.binding.run {
                            if (it.adapterPosition in selectedArray) {
                                overlayView.visibility = View.VISIBLE
                                textView.text = "${selectedArray.indexOf(it.adapterPosition) + 1}"
                            } else {
                                overlayView.visibility = View.GONE
                                textView.text = ""
                            }
                            requestManager
                                .load(items[it.adapterPosition])
                                .thumbnail(0.2f)
                                .into(imageView)

                        }
                    }
                    onClick {
                        if (it.adapterPosition in selectedArray) {
                            selectedArray.remove(it.adapterPosition)
                            adapter?.notifyDataSetChanged()
                        } else {
                            if (selectedArray.size >= max)
                                selectedArray.removeAt(0)
                            selectedArray.add(it.adapterPosition)
                            adapter?.notifyDataSetChanged()
                        }
                    }
                }
                .into(galleyRecyclerView)
            addItemDecoration(GridItemDecoration(3, makeDP(this@GalleryActivity, 3f).toInt()))
        }
    }

    fun makeDP(context: Context, dp: Float): Float = context.displayMetrics.density * dp

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.gallery, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.finish_menu -> {
                if (selectedArray.size == 0)
                    setResult(Activity.RESULT_CANCELED)
                else
                    setResult(Activity.RESULT_OK, Intent().putExtra("paths",
                        items
                            .filterIndexed { index, s -> index in selectedArray }
                            .toTypedArray()))
                finish()
            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
