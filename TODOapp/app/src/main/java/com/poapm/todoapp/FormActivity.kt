package com.poapm.todoapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.poapm.todoapp.MainActivity.Companion.NEW_TASK
import com.poapm.todoapp.MainActivity.Companion.NEW_TASK_KEY
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class FormActivity : AppCompatActivity() {

    private lateinit var edtDate: EditText
    private lateinit var edtTitle: EditText
    private lateinit var edtTime: EditText
    private lateinit var edtDescription: EditText
    private lateinit var btnAdd: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initViews()
    }

    private fun initViews() {
        edtTitle = findViewById(R.id.edtTitle)
        edtDescription = findViewById(R.id.edtDescription)
        edtDate = findViewById(R.id.edtDate)
        edtTime = findViewById(R.id.edtTime)
        btnAdd = findViewById(R.id.btnAdd)

        edtDate.setOnClickListener {
            val nowDate = LocalDate.now()


            val dataPicker =  DatePickerDialog(this,
                { _, year, month, dayOfMonth ->
                    val monthActual = month + 1
                    if(dayOfMonth<10){
                        edtDate.setText("0$dayOfMonth/$monthActual/$year")
                        if(monthActual<10){
                            edtDate.setText("0$dayOfMonth/0$monthActual/$year")
                        }
                    }else{
                        edtDate.setText("$dayOfMonth/$monthActual/$year")
                        if(month<10 ){
                            edtDate.setText("$dayOfMonth/0$monthActual/$year")
                        }
                    }
                },
                nowDate.year,
                nowDate.monthValue - 1,
                nowDate.dayOfMonth
            )
            dataPicker.datePicker.minDate = System.currentTimeMillis()
            dataPicker.show()

        }

        edtTime.setOnClickListener {
            val TimeActual = LocalTime.now()

            TimePickerDialog(this,
                { _, hour, minute ->

                    if(hour < 10){
                        edtTime.setText("0$hour:$minute")
                        if(minute < 10)
                            edtTime.setText("0$hour:0$minute")
                    }else{
                        edtTime.setText("$hour:$minute")
                        if(minute < 10)
                            edtTime.setText("$hour:0$minute")
                    }
                },
                TimeActual.hour,
                TimeActual.minute,
                true
            ).show()
        }

        btnAdd.setOnClickListener {

            if (edtTitle.text.isNotEmpty() && edtDate.text.isNotEmpty() && edtDescription.text.isNotEmpty() && edtTime.text.isNotEmpty()){
                setResult(NEW_TASK, Intent().putExtra(
                    NEW_TASK_KEY,
                    Task(
                        0,
                        edtTitle.text.toString(),
                        edtDescription.text.toString(),
                        LocalDateTime.of(
                            LocalDate.parse(
                                edtDate.text,
                                DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        ),
                        LocalTime.parse(edtTime.text, DateTimeFormatter.ofPattern("HH:mm")))
                    )
                ))
                finish()
            }else{
                Alert("Llena todos los campos para guardar la tarea")
            }
        }
    }

    fun Alert(mensaje: String) =
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
}