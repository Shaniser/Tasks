package com.godelsoft.tasks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.children
import com.godelsoft.tasks.extensions.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataManager.initialize({Toast.makeText(this, "Loading error", Toast.LENGTH_SHORT).show()}) {
            // TODO refresh UI with content
        }

        mainMessage.setOnClickListener(this)

        loadCal()
        nextMonth.setOnClickListener(this)
        prevMonth.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.nextMonth -> {
                curCal.add(Calendar.MONTH, 1)
                loadCal()
            }
            R.id.prevMonth -> {
                curCal.add(Calendar.MONTH, -1)
                loadCal()
            }
            R.id.mainMessage -> {
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
            }
        }

        if (cardToCal.containsKey(v)) {
            selectedDate = cardToCal[v]
            loadCal()
        }
    }



    private var curCal = Calendar.getInstance().apply {
        day = 1
    }
    private var selectedDate = Calendar.getInstance()
    private val cardToCal = HashMap<View, Calendar>()
    @SuppressLint("WrongConstant")
    private fun loadCal() {
        cardToCal.clear()
        val c = curCal.clone() as Calendar
        calMonth.text = c.getDisplayName(Calendar.MONTH, Calendar.LONG_STANDALONE, Locale.getDefault())?.let {
            "${it[0].toUpperCase()}${it.substring(1)}${
                if (c.year != Calendar.getInstance().year) " ${c.year}" else ""
            }"
        }
        c.day = 1

        c.add(Calendar.DAY_OF_MONTH, 1 - c.dayOfWeek)
        for (row in calTable.children) {
            if (row is TableRow && row.id != R.id.dayNamesRow) {
                for (elem in row.children) {
                    loadDayCard(c, elem, curCal.month)
                    c.add(Calendar.DAY_OF_MONTH, 1)
                }
            }
        }
    }

    private fun loadDayCard(c: Calendar, view: View, curMonth: Int) {
        cardToCal[view] = Calendar.getInstance().apply { timeInMillis = c.timeInMillis }
        view.setOnClickListener(this)
        if (c.date == selectedDate.date) {
            (view as CardView).setCardBackgroundColor(resources.getColor(R.color.colorSelection))
        } else {
            (view as CardView).setCardBackgroundColor(resources.getColor(R.color.colorIcons))
        }
        view.apply {
            findViewById<TextView>(R.id.day).apply {
                text = "${c.day}"

                (view as CardView).elevation = 6F
                when {
                    c.date == selectedDate.date -> {
                        setTextColor(resources.getColor(R.color.colorIcons))
                    }
                    c.date == Calendar.getInstance().date -> {
                        setTextColor(resources.getColor(R.color.colorAccent))
                    }
                    c.month != curMonth -> {
                        setTextColor(resources.getColor(R.color.colorSecondaryText))
                        view.elevation = 3F
                    }
                    else -> {
                        setTextColor(resources.getColor(R.color.colorPrimaryText))
                    }
                }
            }
            findViewById<LinearLayout>(R.id.events).apply {
                removeAllViews()
                val lAndE = DataManager.getLessonsAndEventsByDate(c)
                for (lesson in lAndE.first) {
                    addView(lesson.toLittleCard(this@MainActivity))
                }
                for (event in lAndE.second) {
                    addView(event.toLittleCard(this@MainActivity))
                }
            }
        }
    }
}