package com.sunmi.paring.fragment


import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.nitrico.lastadapter.LastAdapter
import com.sunmi.paring.BR
import com.sunmi.paring.util.DataHelper
import com.sunmi.paring.util.NetworkHelper
import com.sunmi.paring.R
import com.sunmi.paring.activity.GalleryActivity
import com.sunmi.paring.databinding.ItemWriteCardBinding
import kotlinx.android.synthetic.main.fragment_write.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class WriteFragment : Fragment() {

    var imageArray = ArrayList<String>()
    lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imageButton.setOnClickListener {
            requestPermission()
        }
        uploadButton.setOnClickListener {
            checkInput()

            setProgressDialog()
            val images = ArrayList<MultipartBody.Part>()
            val thumbfile = File(imageArray.first())
            imageArray.removeAt(0)
            val thumbbody = RequestBody.create(MediaType.parse("image/*"), thumbfile)
            val thumbnail = MultipartBody.Part.createFormData("thumbnail", thumbfile.name, thumbbody)
            imageArray.forEach {
                val file = File(it)
                val body = RequestBody.create(MediaType.parse("image/*"), file)
                images.add(MultipartBody.Part.createFormData("images", file.name, body))
            }
            val title = RequestBody.create(MediaType.parse("text/plain"), titleEdit.text.toString())
            val subTitle = RequestBody.create(MediaType.parse("text/plain"), subTitleEdit.text.toString())
            val desc = RequestBody.create(MediaType.parse("text/plain"), descEdit.text.toString())
            val userId = RequestBody.create(MediaType.parse("text/plain"), DataHelper.dataHelper!!.user_id)
            NetworkHelper.retrofitInstance.createPost(images.toTypedArray(), thumbnail, title, subTitle, desc, userId)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        progressDialog.dismiss()
                        toast("오류가 발생했습니다.")
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                        progressDialog.dismiss()
                    }
                })
        }
        setRecyclerView()
    }

    private fun getPictureOnGallery() {
        startActivityForResult<GalleryActivity>(2000)
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1000
            )
        } else {
            getPictureOnGallery()
        }
    }

    private fun setRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
            LastAdapter(imageArray, BR.item)
                .map<String, ItemWriteCardBinding>(R.layout.item_write_card) {
                    onBind {
                        it.itemView.findViewById<ImageView>(R.id.imageContainer).backgroundColor = Color.TRANSPARENT
                        it.adapterPosition
                        Glide.with(context)
                            .load(imageArray[it.adapterPosition])
                            .into(it.binding.image)
                    }
                    onLongClick {
                        imageArray.removeAt(it.adapterPosition)
                        recyclerView.adapter?.notifyItemRemoved(it.adapterPosition)
                    }
                }
                .into(this)
        }
    }

    private fun checkInput(): Boolean {
        return true
    }

    private fun setProgressDialog() {
        progressDialog = ProgressDialog(context!!)
        progressDialog.setMessage("게시 중 입니다.")
        progressDialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1000) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPictureOnGallery()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2000) {
            imageArray.addAll(data!!.getStringArrayExtra("paths"))
            recyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = WriteFragment()
    }
}
