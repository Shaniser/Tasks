package com.godelsoft.tasks

open class Event (
    var header: String,
    var content: String,
    var date: String,
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