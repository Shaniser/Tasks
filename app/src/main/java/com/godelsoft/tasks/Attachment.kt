package com.godelsoft.tasks

enum class AttachmentType {
    HOMEWORK,
    INFO
}

data class Attachment(
    val id: Int,
    var type: AttachmentType,
    var content: String,
    var isPublic: Boolean,
    var isHidden: Boolean
) {

}