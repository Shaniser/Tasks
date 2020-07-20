package com.godelsoft.tasks

import java.util.*

var Calendar.day: Int
    set(value) {
        set(Calendar.DATE, value)
    }
    get() {
        return get(Calendar.DATE)
    }

var Calendar.month: Int
    set(value) {
        set(Calendar.MONTH, value - 1)
    }
    get() {
        return get(Calendar.MONTH + 1)
    }

var Calendar.year: Int
    set(value) {
        set(Calendar.YEAR, value)
    }
    get() {
        return get(Calendar.YEAR)
    }

val Calendar.date: String
    get() {
        return "$day.$month.$year"
    }