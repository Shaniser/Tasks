package com.godelsoft.tasks

class Lesson(
    header: String,
    content: String,
    date: String,
    timeBegin: String,
    timeEnd: String,
    var classroom: String? = null,
    var teacher: Teacher? = null

) : Event(header, content, date, timeBegin, timeEnd) {

}