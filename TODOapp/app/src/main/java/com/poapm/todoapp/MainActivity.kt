package com.poapm.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    companion object{
        val NEW_TASK = 200
        val NEW_TASK_KEY = "newTask"
    }

    private lateinit var rcvTask: RecyclerView
    private lateinit var btnAddTask: FloatingActionButton

    private lateinit var adapter: TaskAdapter
    private val SAVED_TASKS = "tasks"

    private var tasks = mutableListOf(
        Task(0,"prueba","description", LocalDateTime.now())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        savedInstanceState?.let {
            val savedTasks = it.getParcelableArrayList<Task>(SAVED_TASKS)?.toMutableList() ?: tasks
            tasks  = savedTasks
        }


        initViews()

        setAdapter()
    }

    private fun initViews(){
        rcvTask = findViewById(R.id.rcvTasks)
        btnAddTask = findViewById(R.id.btnAddTask)


        btnAddTask.setOnClickListener{
            startActivityForResult(Intent(this, FormActivity::class.java), NEW_TASK)
        }
    }

    private fun setAdapter(){
        adapter = TaskAdapter(tasks)

        rcvTask.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rcvTask.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.apply {
            putParcelableArrayList(SAVED_TASKS, tasks as ArrayList<Task>)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == NEW_TASK){
            data?.getParcelableExtra<Task>(NEW_TASK_KEY)?.let{
                adapter.add(it)
            }
        }
    }
}