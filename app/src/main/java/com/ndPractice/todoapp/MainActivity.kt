package com.ndPractice.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.ndPractice.todoapp.Database.MyDatabase
import com.ndPractice.todoapp.add_task_bottom_sheet.AddTaskFragment
import com.ndPractice.todoapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottotomSheet = AddTaskFragment()
        bottotomSheet.show(supportFragmentManager, null)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        //initlisteners()

        val database = Room.databaseBuilder(this, MyDatabase::class.java, "my-database")
            .build()
    }

    private fun initlisteners() {
        binding.fab.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .show(AddTaskFragment())
                .commit()
        }
    }

    }
