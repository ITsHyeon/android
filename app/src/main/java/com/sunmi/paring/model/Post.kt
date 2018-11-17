package com.sunmi.paring.model

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class Post(
    var date: Date = Date(),
    var title: String = "",
    var sponsors: ArrayList<User> = ArrayList(),
    var desc: String = "",
    var images: ArrayList<String> = ArrayList(),
    var thumbnail: String = ""
) : Serializable
