package com.godelsoft.tasks.hierarchy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginBottom
import com.godelsoft.tasks.R
import java.util.*

open class Event (
    val id: Int,
    var header: String,
    var content: String,
    var date: Calendar,
    var timeBegin: String,
    var timeEnd: String? = null
){
    private val attachments: ArrayList<Attachment> = arrayListOf()

    fun addAttachment(newAttachment: Attachment) {
        attachments.add(newAttachment)
    }

    fun attachmentsSize(): Int {
        return attachments.size
    }

    fun toLittleCard(context: Context): View {
        return LayoutInflater.from(context).inflate(R.layout.lesson_little_card, null).apply {
            findViewById<CardView>(R.id.calLesson).apply {
                setCardBackgroundColor(
                    when {
                        this@Event is Lesson && type == LessonType.LECTURE -> context.resources.getColor(R.color.colorLessonLecture)
                        this@Event is Lesson && type == LessonType.SEMINAR -> context.resources.getColor(R.color.colorLessonSeminar)
                        else -> context.resources.getColor(R.color.colorEvent)
                    }
                )
                findViewById<TextView>(R.id.eventName).text = header
            }
        }
    }
}