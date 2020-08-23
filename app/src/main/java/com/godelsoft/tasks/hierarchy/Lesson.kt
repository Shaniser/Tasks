package com.godelsoft.tasks.hierarchy

import java.util.*

enum class LessonType {
    LECTURE,
    SEMINAR
}

class Lesson(
    id: Int,
    header: String,
    content: String,
    date: Calendar,
    timeBegin: String,
    timeEnd: String,
    var type: LessonType,
    var classroom: String? = null,
    var teacher: Teacher? = null,
    var isNotEveryWeek: Boolean = false

) : Event(id, header, content, date, timeBegin, timeEnd) {
}