package com.sunmi.paring.model

class Project(
    var title: String = "",
    var posts: ArrayList<Post> = ArrayList(),
    var author: User = User()
)
