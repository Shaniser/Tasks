package com.godelsoft.tasks


import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.godelsoft.tasks.extensions.date
import com.godelsoft.tasks.hierarchy.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


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

    private fun parseDate(str: String) : Calendar{
        val cal = Calendar.getInstance()
        cal.set(str.substring(6).toInt(), str.substring(3, 5).toInt(), str.substring(0, 2).toInt())
        return cal
    }

    private fun loadEventsResponser(response: String) {
        val arr = JSONArray(response)
        for(i in 0..arr.length()) {
            val record = JSONObject(arr[i].toString());
            val cal = parseDate(record.getString("date"))

            Log.d("Result", "Calendar: " + cal.date)

            val id = record.getInt("id")
            var event = Lesson(id,
                record.getString("header"),
                record.getString("content"),
                cal,
                record.getString("timeBegin"),
                record.getString("timeEnd"),
                if (record.getString("type") == "LECTURE") LessonType.LECTURE else LessonType.SEMINAR,
                record.getString("classRoom")
            )
            events.put(record.getInt("id"), event)
            if (!dateToEventIdArr.containsKey(cal.date)) {
                dateToEventIdArr[cal.date] = arrayListOf()
            }
            dateToEventIdArr[cal.date]?.add(id)

        }
    }

    private fun loadEvents() {
        val queue = Volley.newRequestQueue(MainActivity.appContext)
        val request: StringRequest =
            object : StringRequest(
                Request.Method.POST, "https://tasks-webserver.000webhostapp.com/main.php",
                Response.Listener() {
                    response -> loadEventsResponser(response)
                },
                Response.ErrorListener() {
                    error -> Log.d("Result", "Error: " + error.toString())
                }) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["sessionID"] = "69710995422b20b0f35b762855660c2be2e1203bb5434d92bae00e1e7a62ec7d"
                    params["action"] = "loadEvents"
                    return params
                }

                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["Content-Type"] = "application/x-www-form-urlencoded"
                    return params
                }
            }
        queue.add(request)

        // TODO remove
//        val count = 10000
//        val c = Calendar.getInstance()
//        c.add(Calendar.DAY_OF_MONTH, -(count / 2))
//        for (i in 0..count) {
//            for (j in 0..Random().nextInt(4)) {
//                val start = arrayListOf(
//                    Random().nextInt(13) + 8,
//                    Random().nextInt(12) * 5 % 60
//                )
//                events[i * count * 10 + j] = when(
//                        if (c.get(Calendar.DAY_OF_WEEK).let { it == Calendar.SUNDAY || it == Calendar.SATURDAY })
//                            0
//                        else
//                            Random().nextInt(3)
//                    ) {
//                    0 -> Event(
//                        i * count * 10 + j,
//                        arrayListOf("Уборка", "Домашняя работа", "Занятие спортом").random(),
//                        "description",
//                        Calendar.getInstance().apply { timeInMillis = c.timeInMillis },
//                        "${start[0]}:${if (start[1] < 10) "0${start[1]}" else start[1]}"
//                    )
//                    else -> Lesson(
//                        i * count * 10 + j,
//                        arrayListOf("Матанализ", "ЛинАл", "Алгоритмы компьютерной графики").random(),
//                        "description",
//                        Calendar.getInstance().apply { timeInMillis = c.timeInMillis },
//                        "${start[0]}:${if (start[1] < 10) "0${start[1]}" else start[1]}",
//                        "${((start[1] + 95) / 60 + start[0]) % 24}:${((start[1] + 95) % 60).let { if (it < 10) "0$it" else it }}",
//                        arrayListOf(LessonType.LECTURE, LessonType.SEMINAR).random(),
//                        arrayListOf("777л", "428ю", "218", null).random()
//                    )
//                }
//
//                if (!dateToEventIdArr.containsKey(c.date)) {
//                    dateToEventIdArr[c.date] = arrayListOf()
//                }
//                dateToEventIdArr[c.date]?.add(i * count * 10 + j)
//            }
//            c.add(Calendar.DAY_OF_MONTH, 1)
//        }
        // TODO remove
    }

    private fun loadTeachers() {
        // TODO load teachers from DB
    }

    fun addEvent(event: Event) {
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