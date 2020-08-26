package com.godelsoft.tasks.hierarchy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.godelsoft.tasks.R
import kotlinx.android.synthetic.main.event_card.view.*
import kotlinx.android.synthetic.main.teacher_card.view.*
import java.util.*

data class Teacher (
    var id: Int,
    var name: String,
    var email: String? = null,
    var phoneNumber: String? = null,
    var department: String? = null
){
    fun toCard(context: Context): View {
        return LayoutInflater.from(context).inflate(R.layout.teacher_card, null).apply {
            findViewById<CardView>(R.id.event).apply {
                if (this@Teacher.email != null) {
                    nameET.setText("${this@Teacher.email}")
                }
                if (phoneNumber != null) numberET.setText("${this@Teacher.phoneNumber}")
                nameET.setText("${this@Teacher.name}")

            }
        }
    }
}