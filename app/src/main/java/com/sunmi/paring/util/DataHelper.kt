package com.sunmi.paring.util

import com.sunmi.paring.model.Post
import com.sunmi.paring.model.Project

class DataHelper {

    var user_id = ""
    var projectList = ArrayList<Project>()


    var selectProject: Project = Project()
    var selectPost: Post = Post()
    companion object {
        var dataHelper: DataHelper? = null
            get() {
                if (field == null)
                    dataHelper = DataHelper()
                return field
            }
    }
}
