package com.godelsoft.tasks

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.godelsoft.tasks.extensions.date
import com.godelsoft.tasks.extensions.day
import com.godelsoft.tasks.extensions.month
import com.godelsoft.tasks.extensions.year
import com.godelsoft.tasks.hierarchy.Event
import com.godelsoft.tasks.hierarchy.Lesson
import com.godelsoft.tasks.hierarchy.LessonType
import com.godelsoft.tasks.hierarchy.Teacher
import kotlinx.android.synthetic.main.activity_event.*
import java.util.*


class EventActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)



        val arguments = intent.extras
        val id: Int? = arguments?.getInt("id")
        val event: Event? = id?.let { DataManager.getEventById(it) }
        Log.d("TAG", id.toString())
        val spinner: Spinner = lessonTypeS as Spinner
        val context = this
        val list = mutableListOf(
            "Лекция",
            "Семинар"
        )
        list.add(0,"Лекция или семинар?")

        if (id == null) {
            isNotEveryWeekCB.visibility = View.GONE
            lessonTypeS.visibility = View.GONE
            classroomET.visibility = View.GONE
        }

        val adapter:ArrayAdapter<String> = object: ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            list
        ){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view:TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                view.setTypeface(view.typeface, Typeface.BOLD)

                if (position == spinner.selectedItemPosition && position !=0 ){
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                if(position == 0){
                    view.setTextColor(Color.LTGRAY)
                }
                return view
            }

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }
        }

        spinner.adapter = adapter

        isLessonCB.setOnClickListener {
            if (!isLessonCB.isChecked) {
                isNotEveryWeekCB.visibility = View.GONE
                lessonTypeS.visibility = View.GONE
                classroomET.visibility = View.GONE
                //teacherET.visibility = View.GONE
            } else {
                isNotEveryWeekCB.visibility = View.VISIBLE
                lessonTypeS.visibility = View.VISIBLE
                classroomET.visibility = View.VISIBLE
                //teacherET.visibility = View.VISIBLE
            }
        }

        event?.apply {
            headerET.setText(this.header)
            contentET.setText(content)
            dateET.setText(date.date)
            timeBeginEt.setText(timeBegin)
            timeEndET.setText(timeEnd ?: "")
            color.background = when {
                this is Lesson && type == LessonType.LECTURE -> resources.getDrawable(R.color.colorLessonLecture)
                this is Lesson && type == LessonType.SEMINAR -> resources.getDrawable(R.color.colorLessonSeminar)
                else -> resources.getDrawable(R.color.colorEvent)
            }
            if (this is Lesson) {
                isLessonCB.toggle()
                if (type == LessonType.LECTURE)  spinner.setSelection(1)
                else spinner.setSelection(2)
                classroomET.setText(classroom ?: "")

                //if (teacher == null) teacherET.setText("")
                //else //teacherET.setText(teacher.toString())

            } else if (!isLessonCB.isChecked) {
                isNotEveryWeekCB.visibility = View.GONE
                lessonTypeS.visibility = View.GONE
                classroomET.visibility = View.GONE
                //teacherET.visibility = View.GONE
            }
        }

        saveButton.setOnClickListener {
            if (headerET.text.toString() != "" && contentET.text.toString() != "" && dateET.text.toString() != "" && timeBeginEt.text.toString() != "") {
                val newDate: Calendar = event?.date ?: Calendar.getInstance()
                newDate.day = dateET.text.toString().split(".")[0].toInt()
                newDate.month = dateET.text.toString().split(".")[1].toInt()
                newDate.year = dateET.text.toString().split(".")[2].toInt()
                if (isLessonCB.isChecked) {
                    val myLessonType : LessonType = if (spinner.selectedItemPosition == 0) LessonType.LECTURE else LessonType.SEMINAR
                    if(id != null) {
                        var newLesson: Lesson = Lesson(
                            id,
                            headerET.text.toString(),
                            contentET.text.toString(),
                            newDate,
                            timeBeginEt.text.toString(),
                            timeEndET.text.toString(),
                            myLessonType,
                            classroomET.text.toString(),
                            null,
                            isNotEveryWeekCB.isChecked
                        )
                        DataManager.saveEvent(newLesson)
                    } else {
                        var newLesson: Lesson = Lesson(
                            -1,
                            headerET.text.toString(),
                            contentET.text.toString(),
                            newDate,
                            timeBeginEt.text.toString(),
                            timeEndET.text.toString(),
                            myLessonType,
                            classroomET.text.toString(),
                            null,
                            isNotEveryWeekCB.isChecked
                        )
                        DataManager.addEvent(newLesson)
                    }
                    Toast.makeText(this, "Успешно сохранено!", Toast.LENGTH_SHORT).show()
                } else {
                    if (id != null) {
                        var newEvent: Event = Event(
                            id,
                            headerET.text.toString(),
                            contentET.text.toString(),
                            newDate,
                            timeBeginEt.text.toString(),
                            timeEndET.text.toString()
                        )
                        DataManager.saveEvent(newEvent)
                    } else {
                        var newEvent: Event = Event(
                            -1,
                            headerET.text.toString(),
                            contentET.text.toString(),
                            newDate,
                            timeBeginEt.text.toString(),
                            timeEndET.text.toString()
                        )
                        DataManager.addEvent(newEvent)
                    }
                    Toast.makeText(this, "Успешно сохранено!", Toast.LENGTH_SHORT).show()
                }
            } else Toast.makeText(this, "Введите все обязательные поля!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textView3 -> {
                val intent = Intent(this, TeacherActivity::class.java)
                startActivity(intent)
                //TODO realise extra (не знаю что передавать чтоб изменять препода)
            }
        }
    }
}