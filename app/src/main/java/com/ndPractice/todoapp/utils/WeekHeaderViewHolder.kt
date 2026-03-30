package com.ndPractice.todoapp.utils

import android.view.View
import com.kizitonwose.calendar.view.ViewContainer
import com.ndPractice.todoapp.databinding.CalendarWeekHeaderBinding

class WeekHeaderViewHolder(view: View) : ViewContainer(view) {

     val monthName = CalendarWeekHeaderBinding.bind(view).monthName
}