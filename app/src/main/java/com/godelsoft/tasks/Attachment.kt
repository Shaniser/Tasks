package com.godelsoft.tasks

enum class AttachmentType {
    HOMEWORK,
    INFO
}

data class Attachment(
    var type: AttachmentType,
    var content: String,
    var isPublic: Boolean,
    var isHidden: Boolean
) {

}