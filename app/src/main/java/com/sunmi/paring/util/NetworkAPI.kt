package com.sunmi.paring.util

import com.sunmi.paring.model.PostListWrapper
import com.sunmi.paring.model.ProjectListWrapper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkAPI {

    @FormUrlEncoded
    @POST("/auth/signin")
    fun signIn(
        @Field("user_id") user_id: String,
        @Field("user_pw") user_pw: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/auth/signup")
    fun signUp(
        @Field("user_id") user_id: String,
        @Field("user_pw") user_pw: String,
        @Field("name") name: String,
        @Field("phone") phone: String
    ): Call<ResponseBody>

    @GET("user")
    fun getAllUser(): Call<ResponseBody>

    @GET("user/{id}")
    fun getUserById(@Path("id") userId: String): Call<ResponseBody>

    @Multipart
    @POST("post")
    fun createPost(
        @Part pictures: Array<MultipartBody.Part>,
        @Part thumbnail: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("subtitle") subTitle: RequestBody,
        @Part("desc") desc: RequestBody,
        @Part("user") id: RequestBody
    ): Call<ResponseBody>

    @GET("post")
    fun getPostAll(): Call<PostListWrapper>

    @GET("project")
    fun getProjectAll(): Call<ProjectListWrapper>
}
