package com.ai.tirios.dataclasses

import android.graphics.Bitmap
import android.net.Uri
import com.ibm.watson.assistant.v2.model.RuntimeResponseGeneric
import java.io.Serializable

class Message : Serializable{
    var id: String? = null
    var message: String? = null
    var url: String? = null
    var uri: Uri? = null
    var title: String? = null
    var description: String? = null
    var btnOption1 : String? = null
    var btnOption2 : String? = null
    var btnOption3 : String? = null
    var btnOption4 : String? = null
    var btnOption5 : String? = null
    var imageBitMap : Bitmap? = null
    var type: Type
    var isReplied = false

    constructor() {
        type = Type.TEXT
    }

    constructor(r: RuntimeResponseGeneric) {
        message = ""
        title = r.title()
        description = r.description()
        url = r.source()
        id = "2"
        type = Type.IMAGE
        if (r.options().size==1)
            btnOption1 = r.options().get(0).label
        if (r.options().size==2)
            btnOption2 = r.options().get(1).label
        if (r.options().size==3)
            btnOption3 = r.options().get(2).label
        if (r.options().size==4)
            btnOption4 = r.options().get(3).label
        if (r.options().size==5)
            btnOption5 = r.options().get(4).label
    }

    enum class Type {
        TEXT, IMAGE, VIDEO, OPTIONS, TIME, BUTTON
    }
}