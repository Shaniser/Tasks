package com.godelsoft.tasks.hierarchy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.godelsoft.tasks.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.event_card.view.*
import java.io.Serializable
import java.util.*

open class Event (
    val id: Int,
    var header: String,
    var content: String,
    var date: Calendar,
    var timeBegin: String,
    var timeEnd: String? = null
) {
    private val attachments: ArrayList<Attachment> = arrayListOf()

    fun addAttachment(newAttachment: Attachment) {
        attachments.add(newAttachment)
    }

    fun attachmentsSize(): Int {
        return attachments.size
    }

    fun toLittleCard(context: Context): View {
        return LayoutInflater.from(context).inflate(R.layout.lesson_little_card, null).apply {
            calLesson.apply {
                setCardBackgroundColor(
                    when {
                        this@Event is Lesson && type == LessonType.LECTURE -> context.resources.getColor(R.color.colorLessonLecture)
                        this@Event is Lesson && type == LessonType.SEMINAR -> context.resources.getColor(R.color.colorLessonSeminar)
                        else -> context.resources.getColor(R.color.colorEvent)
                    }
                )
                eventName.text = header
            }
        }
    }

    fun toCard(context: Context): View {
        return LayoutInflater.from(context).inflate(R.layout.event_card, null).apply {
            findViewById<CardView>(R.id.event).apply {
                color.background = when {
                    this@Event is Lesson && type == LessonType.LECTURE -> context.resources.getDrawable(R.color.colorLessonLecture)
                    this@Event is Lesson && type == LessonType.SEMINAR -> context.resources.getDrawable(R.color.colorLessonSeminar)
                    else -> context.resources.getDrawable(R.color.colorEvent)
                }
                if (this@Event is Lesson && this@Event.classroom != null) {
                    eventHeader.text = "${this@Event.classroom} $header"
                } else {
                    eventHeader.text = header
                }
                findViewById<TextView>(R.id.time).text = "${date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())} ${
                    if (timeEnd != null) "$timeBegin - $timeEnd" else timeBegin
                }"
            }
        }
    }
}