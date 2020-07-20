package com.godelsoft.tasks.hierarchy

import java.util.*

class Lesson(
    id: Int,
    header: String,
    content: String,
    date: Calendar,
    timeBegin: String,
    timeEnd: String,
    var classroom: String? = null,
    var teacher: Teacher? = null

) : Event(id, header, content, date, timeBegin, timeEnd) {

}