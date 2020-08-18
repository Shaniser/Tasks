package com.godelsoft.tasks

import com.godelsoft.tasks.extensions.date
import com.godelsoft.tasks.extensions.day
import com.godelsoft.tasks.hierarchy.*
import java.lang.Exception
import java.util.*
import kotlin.Comparator
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

        // TODO remove
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, -20)
        for (i in 0..40) {
            for (j in 0..Random().nextInt(4)) {
                events[i * 100 + j] = when(Random().nextInt(3)) {
                    0 -> Event(
                        i * 100 + j,
                        arrayListOf("Уборка", "Домашняя работа", "Занятие спортом").random(),
                        "description",
                        Calendar.getInstance().apply { timeInMillis = c.timeInMillis },
                        "${Random().nextInt(13) + 8}:${Random().nextInt(60).let { if (it < 10) "0$it" else "$it" }}"
                    )
                    else -> Lesson(
                        i * 100 + j,
                        arrayListOf("Матанализ", "ЛинАл", "Алгоритмы компьютерной графики").random(),
                        "description",
                        Calendar.getInstance().apply { timeInMillis = c.timeInMillis },
                        "${Random().nextInt(13) + 8}:${Random().nextInt(60).let { if (it < 10) "0$it" else "$it" }}",
                        "time end",
                        arrayListOf(LessonType.LECTURE, LessonType.SEMINAR).random(),
                        arrayListOf("777л", "428ю", "218", null).random()
                    )
                }
            }
            c.add(Calendar.DAY_OF_MONTH, 1)
        }
        // TODO remove
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
            sortWith(compareBy{
                it.timeBegin.split(":").toTypedArray().let { t -> t[0].toInt() * 60 + t[1].toInt() + (it.date.timeInMillis / 1000 / 60 / 60 / 24) * 24 * 60}
            })
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