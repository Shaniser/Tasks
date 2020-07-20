package com.godelsoft.tasks

import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object DataManager {
    private val events: HashMap<Int, Event> = hashMapOf()
    private val teachers: HashMap<Int, Teacher> = hashMapOf()

    fun initialize(onError: () -> Unit, onSuccess: () -> Unit) {
        try {
            loadTeachers()
            loadEvents()
            onSuccess()
        } catch (e: Exception) {
            onError()
        }
    }

    fun createId(): Int {
        // TODO BD creates unique ID
        return 0
    }

    private fun loadEvents() {
        // TODO load events from DB
    }

    private fun loadTeachers() {
        // TODO load teachers from DB
    }

    fun addEvent(event: Event) {
        events[event.id] = event
        // TODO upload event to DB
    }

    fun addAttachment(attachment: Attachment, eventId: Int) {
        events[eventId]?.addAttachment(attachment)
        // TODO upload attachment to DB
    }

    fun addTeacher(teacher: Teacher) {
        teachers[teacher.id] = teacher
        // TODO upload teacher info to DB
    }

    private fun getEventsByDate(calendar: Calendar): ArrayList<Event> {
        return arrayListOf<Event>().apply {
            for (event in events.values) {
                if (event.date.date == calendar.date) {
                    add(event)
                }
            }
            sortBy { event -> event.date }
        }
    }

    fun getLessonsAndEventsByDate(calendar: Calendar): Pair<ArrayList<Lesson>, ArrayList<Event>> {
        return Pair(arrayListOf<Lesson>(), arrayListOf<Event>()).apply {
            val events = getEventsByDate(calendar)
            for (event in events) {
                if (event is Lesson) {
                    first.add(event)
                } else {
                    second.add(event)
                }
            }
        }
    }
}