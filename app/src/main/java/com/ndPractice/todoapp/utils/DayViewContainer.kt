package com.ndPractice.todoapp.utils

import android.view.View
import com.kizitonwose.calendar.view.ViewContainer
import com.ndPractice.todoapp.databinding.CalendarDayLayoutBinding

class DayViewContainer(view: View) : ViewContainer(view) {

     val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
}