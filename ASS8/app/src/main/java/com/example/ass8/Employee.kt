package com.example.ass8

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Employee (
    @Expose
    @SerializedName("employee_name") val emp_name:String,

    @Expose
    @SerializedName("employee_gender") val emp_gender:String,

    @Expose
    @SerializedName("employee_email") val emp_email:String,

    @Expose
    @SerializedName("employee_salary") val emp_salary:Int){}