package com.godelsoft.tasks

import java.util.*
import kotlin.collections.ArrayList

open class Event (
    val id: Int,
    var header: String,
    var content: String,
    var date: Calendar,
    var timeBegin: String,
    var timeEnd: String? = null
){
    private val attachments: ArrayList<Attachment> = arrayListOf()

    fun addAttachment(newAttachment: Attachment) {
        attachments.add(newAttachment)
    }

    fun attachmentsSize(): Int {
        return attachments.size
    }
}