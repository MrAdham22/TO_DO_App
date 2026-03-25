package com.ndPractice.todoapp

import android.app.Application
import com.ndPractice.todoapp.Database.MyDatabase

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MyDatabase.creatDatabase(this)
    }
}