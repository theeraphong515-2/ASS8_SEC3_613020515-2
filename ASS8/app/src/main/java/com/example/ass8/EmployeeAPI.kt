package com.example.ass8



import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface EmployeeAPI {
    @GET("allemp")
    fun retrieveEmployee(): Call<List<Employee>>

    @FormUrlEncoded
    @POST("emp")
    fun insertEmp(
        @Field("employee_name")emp_name:String,
        @Field("employee_gender")emp_gender:String,
        @Field("employee_email")emp_email:String,
        @Field("employee_salary")emp_salary:Int
    ):Call<Employee>
}