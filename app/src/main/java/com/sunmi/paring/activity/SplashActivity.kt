package com.sunmi.paring.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sunmi.paring.util.DataHelper
import com.sunmi.paring.util.NetworkHelper
import com.sunmi.paring.R
import kotlinx.android.synthetic.main.activity_splash.*
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginButton.setOnClickListener {
            if (checkInput()) {
                setProgressDialog()
                NetworkHelper.retrofitInstance.signIn(idEdit.text.toString(), pwEdit.text.toString())
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            progressDialog.dismiss()
                            toast("네트워크를 확인해 주세요")
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.code() != 200) {
                                toast("아이디와 비밀번호를 확인해 주세요")
                                progressDialog.dismiss()
                            } else {
                                progressDialog.dismiss()
                                DataHelper.dataHelper
                                val json = JSONObject(response.body()!!.string())
                                json.getString("data")
                                DataHelper.dataHelper!!.user_id = json.getString("data")
                                startMain()
                            }
                        }
                    })
            } else {
                toast("아이디와 비밀번호를 확인해 주세요")
            }
        }
    }

    private fun startMain() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun setProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("로그인 중")
        progressDialog.show()
    }

    private fun checkInput(): Boolean {
        if (idEdit.text.toString().isEmpty())
            return false
        if (pwEdit.text.toString().isEmpty())
            return false
        return true
    }
}
