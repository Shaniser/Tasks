package com.godelsoft.tasks.hierarchy

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