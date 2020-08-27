package com.godelsoft.tasks.hierarchy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import com.godelsoft.tasks.R
import kotlinx.android.synthetic.main.teacher_little_card.view.*

data class Teacher (
    var id: Int,
    var name: String,
    var email: String? = null,
    var phoneNumber: String? = null,
    var department: String? = null
){
//    fun toLittleCard(context: Context): View {
//        return LayoutInflater.from(context).inflate(R.layout.teacher_little_card, null)
//    }
    //TODO добавить мини карточку чтоб красиво переходить к TeacherEvent(никак не получилось, выкидывает ошибку все время)
}