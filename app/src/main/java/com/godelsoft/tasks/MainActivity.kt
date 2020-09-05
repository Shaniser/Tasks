package com.godelsoft.tasks

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.children
import com.godelsoft.tasks.extensions.*
import com.godelsoft.tasks.hierarchy.Event
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.event_card.*
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.appContext = applicationContext
        setContentView(R.layout.activity_main)

        DataManager.initialize({Toast.makeText(this, "Loading error", Toast.LENGTH_SHORT).show()}) {
            // TODO refresh UI with content
        }

        mainMessage.setOnClickListener(this)
        DataManager.getEventsByDate(Calendar.getInstance()).let {
            var ans: Event? = null
            for (e in it) {
                val start = Calendar.getInstance()
                e.timeBegin.split(":").toTypedArray().apply {
                    start.hour = this[0].toInt()
                    start.minute = this[1].toInt()
                }
                val end: Calendar? = Calendar.getInstance().let endLet@{ end ->
                    if (e.timeEnd != null) {
                        e.timeEnd!!.split(":").toTypedArray().apply {
                            end.hour = this[0].toInt()
                            end.minute = this[1].toInt()
                        }
                        return@endLet end
                    } else return@endLet null
                }
                Calendar.getInstance().apply {
                    if (start.before(this)) {
                        if ((end != null && end.after(this)) || end == null) {
                            ans = e
                        }
                    }
                }

            }
            ans?.toCard(this)
        }?.apply {
            curEventLay.addView(this)
        }
        loadCal()

//        event.setOnClickListener(this)
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
            R.id.event -> {
                val intent = Intent(this, EventActivity::class.java)
                intent.putExtra("id", cardToEventId[v])
                startActivity(intent)
            }
            R.id.addButton -> {
                val intent = Intent(this, EventActivity::class.java)
                //intent.putExtra("id", null)
                startActivity(intent)
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
        loadDayEvents()
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

    private val cardToEventId = HashMap<View, Int>()

    private fun loadDayEvents() {
        cardToEventId.clear()
        findViewById<TextView>(R.id.curDate).text = selectedDate.date
        findViewById<LinearLayout>(R.id.curDateEvents).apply {
            removeAllViews()
            val lAndE = DataManager.getLessonsAndEventsByDate(selectedDate)
            for (l in lAndE.first) addView(l.toCard(this@MainActivity).apply { cardToEventId[this.findViewById(R.id.event)] = l.id })
            for (e in lAndE.second ) addView(e.toCard(this@MainActivity).apply { cardToEventId[this.findViewById(R.id.event)] = e.id })
        }
    }

    companion object {
        lateinit var appContext: Context;
    }
}