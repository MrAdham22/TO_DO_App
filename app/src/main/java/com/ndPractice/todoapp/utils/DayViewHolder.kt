package com.ndPractice.todoapp.utils

import android.view.View
import com.kizitonwose.calendar.view.ViewContainer
import com.ndPractice.todoapp.databinding.CalendarDayLayoutBinding

class DayViewHolder(view: View) : ViewContainer(view) {

    val rootView: View = view

    val dayNumberTextView = CalendarDayLayoutBinding.bind(view).calendarDayText
    val dayNameTextView = CalendarDayLayoutBinding.bind(view).dayName
}