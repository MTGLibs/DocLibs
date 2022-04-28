package com.wxiwei.office.model

import java.io.Serializable

class FileModel(
    var id: String,
    var path: String,
    var name: String,
    var size: Long,
    var date: Long,
    var favourite: Boolean
) : Serializable