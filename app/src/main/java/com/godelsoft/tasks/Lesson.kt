package com.godelsoft.tasks

class Lesson(
    header: String,
    content: String,
    date: String,
    timeBegin: String,
    timeEnd: String,
    var classroom: String,
    var teacher: String? = null // TODO сделать класс препод (почта и тд)

) : Event(header, content, date, timeBegin, timeEnd) {

}