package com.godelsoft.tasks.extensions

import java.util.*

var Calendar.second: Int
    set(value) {
        set(Calendar.SECOND, value)
    }
    get() {
        return get(Calendar.SECOND)
    }

var Calendar.minute: Int
    set(value) {
        set(Calendar.MINUTE, value)
    }
    get() {
        return get(Calendar.MINUTE)
    }

var Calendar.hour: Int
    set(value) {
        set(Calendar.HOUR_OF_DAY, value)
    }
    get() {
        return get(Calendar.HOUR_OF_DAY)
    }

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
        return get(Calendar.MONTH) + 1
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

val Calendar.dayOfWeek: Int
    get() {
        return (get(Calendar.DAY_OF_WEEK) - 2 + 7) % 7 + 1
    }