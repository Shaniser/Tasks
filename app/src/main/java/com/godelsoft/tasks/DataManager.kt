package com.godelsoft.tasks

import com.beust.klaxon.Klaxon
import com.godelsoft.tasks.extensions.date
import com.godelsoft.tasks.extensions.day
import com.godelsoft.tasks.hierarchy.*
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class LessonInfo(val id: String, val header: String, val content: String, val description: String, val date: String, val timeBegin: String, val timeEnd: String, val type: String, val classroom: String, val teacher: String, val isNotEveryWeek: String)

object DataManager {

    val test: Event? = null

    private val dateToEventIdArr = hashMapOf<String, ArrayList<Int>>()
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
    val testName: String = "[{\"id\":\"1\",\"header\":\"\\u041c\\u0430\\u0442\\u0430\\u043d\\u0430\\u043b\\u0438\\u0437\",\"content\":\"description\",\"date\":\"30.08.2020\",\"timeBegin\":\"12:00\",\"timeEnd\":\"13:30\",\"type\":\"LECTURE\",\"classroom\":\"666\",\"teacher\":\"\\u0413\\u043e\\u043b\\u0443\\u0431\\u043a\\u043e\\u0432\",\"isNotEveryWeek\":\"0\"},{\"id\":\"2\",\"header\":\"\\u041b\\u0438\\u043d\\u0430\\u043b\",\"content\":\"description\",\"date\":\"30.08.2020\",\"timeBegin\":\"13:40\",\"timeEnd\":\"15:10\",\"type\":\"SEMINAR\",\"classroom\":\"228\",\"teacher\":\"\\u0421\\u0442\\u0435\\u043f\\u0430\\u043d\\u043e\\u0432\",\"isNotEveryWeek\":\"0\"},{\"id\":\"3\",\"header\":\"\\u0424\\u0438\\u0437\\u0440\\u0430\",\"content\":\"description\",\"date\":\"30.08.2020\",\"timeBegin\":\"15:20\",\"timeEnd\":\"16:50\",\"type\":\"SEMINAR\",\"classroom\":\"\\u0421\\u041a\",\"teacher\":\"\\u0411\\u0440\\u044b\\u0437\\u0433\\u0430\\u043b\\u043e\\u0432\",\"isNotEveryWeek\":\"0\"},{\"id\":\"4\",\"header\":\"\\u0413\\u0440\\u0430\\u0444\\u0438\\u043a\\u0430\",\"content\":\"description\",\"date\":\"31.08.2020\",\"timeBegin\":\"12:00\",\"timeEnd\":\"13:30\",\"type\":\"SEMINAR\",\"classroom\":\"400\\u044e\",\"teacher\":\"\\u0412\\u0438\\u0448\\u043d\\u044f\\u043a\\u043e\\u0432\",\"isNotEveryWeek\":\"0\"},{\"id\":\"5\",\"header\":\"\\u041c\\u0430\\u0442\\u0430\\u043d\\u0430\\u043b\\u0438\\u0437\",\"content\":\"description\",\"date\":\"31.08.2020\",\"timeBegin\":\"13:40\",\"timeEnd\":\"15:10\",\"type\":\"SEMINAR\",\"classroom\":\"242\",\"teacher\":\"\\u0427\\u0435\\u0442\\u0432\\u0435\\u0440\\u0438\\u043a\\u043e\\u0432\",\"isNotEveryWeek\":\"0\"},{\"id\":\"6\",\"header\":\"\\u041c\\u0430\\u0442\\u0430\\u043d\\u0430\\u043b\\u0438\\u0437\",\"content\":\"description\",\"date\":\"31.08.2020\",\"timeBegin\":\"15:20\",\"timeEnd\":\"16:50\",\"type\":\"SEMINAR\",\"classroom\":\"242\",\"teacher\":\"\\u0427\\u0435\\u0442\\u0432\\u0435\\u0440\\u0438\\u043a\\u043e\\u0432\",\"isNotEveryWeek\":\"0\"},{\"id\":\"7\",\"header\":\"\\u041b\\u0438\\u043d\\u0430\\u043b\",\"content\":\"description\",\"date\":\"01.09.2020\",\"timeBegin\":\"12:00\",\"timeEnd\":\"13:30\",\"type\":\"LECTURE\",\"classroom\":\"300\",\"teacher\":\"\\u0421\\u0442\\u0435\\u043f\\u0430\\u043d\\u043e\\u0432\",\"isNotEveryWeek\":\"0\"},{\"id\":\"8\",\"header\":\"\\u0413\\u0440\\u0430\\u0444\\u0438\\u043a\\u0430\",\"content\":\"description\",\"date\":\"01.09.2020\",\"timeBegin\":\"13:40\",\"timeEnd\":\"15:10\",\"type\":\"LECTURE\",\"classroom\":\"400\\u044e\",\"teacher\":\"\\u0412\\u0438\\u0448\\u043d\\u044f\\u043a\\u043e\\u0432\",\"isNotEveryWeek\":\"0\"},{\"id\":\"9\",\"header\":\"\\u0413\\u0440\\u0430\\u0444\\u0438\\u043a\\u0430\",\"content\":\"description\",\"date\":\"01.09.2020\",\"timeBegin\":\"15:20\",\"timeEnd\":\"16:50\",\"type\":\"LECTURE\",\"classroom\":\"400\\u044e\",\"teacher\":\"\\u0412\\u0438\\u0448\\u043d\\u044f\\u043a\\u043e\\u0432\",\"isNotEveryWeek\":\"0\"}]"

    fun getEventById(id: Int): Event? {
        return events[id]
    }

    fun getTeacherById(id: Int): Teacher? {
        return teachers[id]
    }

    fun createId(): Int {
        // TODO BD creates unique ID
        return 0
    }

    fun saveEvent(event: Event) {
        // TODO save or edit on DB
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

    fun getEventsByDate(calendar: Calendar): ArrayList<Event> {
        return arrayListOf<Event>().apply {
            dateToEventIdArr[calendar.date]?.apply dates@{
                for (eventId in this@dates)  {
                    events[eventId]?.let { this@apply.add(it) }
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