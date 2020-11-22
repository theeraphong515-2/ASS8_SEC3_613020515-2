package com.example.ass8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_dialog_layout.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var EmployeeList = arrayListOf<Employee>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.adapter = EmployeeAdapter(this.EmployeeList, applicationContext)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        callEmployeedata()
    }

    fun callEmployeedata() {
        EmployeeList.clear()
        val serv: EmployeeAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(EmployeeAPI::class.java)

        serv.retrieveEmployee()
            .enqueue(object : Callback<List<Employee>> {
                override fun onResponse(
                    call: Call<List<Employee>>,
                    response: Response<List<Employee>>
                ) {
                    response.body()?.forEach {
                        EmployeeList.add(Employee(it.emp_name, it.emp_gender, it.emp_email, it.emp_salary))
                    }
                    recycler_view.adapter = EmployeeAdapter(EmployeeList, applicationContext)
                }

                override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })

    }
    fun addEmployee(view:View){
        val mDialogView=LayoutInflater.from(this).inflate(R.layout.add_dialog_layout,null)
        val myBuilder=AlertDialog.Builder(this)
        myBuilder.setView(mDialogView)
        val mAlertDialog = myBuilder.show()
        mAlertDialog.btn_add.setOnClickListener() {
            var gender = ""
            if (mAlertDialog.btn_male.isChecked){
                gender="Male"
            }else if (mAlertDialog.btn_female.isChecked){
                gender="Female"
            }
            val serv: EmployeeAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EmployeeAPI::class.java)
            serv.insertEmp(
                mAlertDialog.edt_name.text.toString(),
                gender,
                mAlertDialog.edt_email.text.toString(),
                mAlertDialog.edt_salary.text.toString().toInt()).enqueue(object : Callback<Employee> {
                override fun onResponse(call:Call<Employee>, response: Response<Employee>) {
                    if (response.isSuccessful()){
                        Toast.makeText(applicationContext, "Successfully Inserted", Toast.LENGTH_LONG).show()
                        EmployeeList.add(
                            Employee(
                                mAlertDialog.edt_name.text.toString(),
                                gender,
                                mAlertDialog.edt_email.text.toString(),
                                mAlertDialog.edt_salary.text.toString().toInt()
                            )
                        )
                        mAlertDialog.dismiss()
                    }else{
                        Toast.makeText(applicationContext, "Error on failure", Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<Employee>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error onFailure"+ t.message,Toast.LENGTH_LONG).show()
                }
            })
        }
        mAlertDialog.btn_cancel.setOnClickListener() {
            mAlertDialog.dismiss()
        }

    }
}