package com.godelsoft.tasks

data class Teacher (
    var id: Int,
    var name: String,
    var email: String? = null,
    var phoneNumber: String? = null,
    var department: String? = null
){

}