package main.fragments.list_fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.WeekDayBinder
import com.ndPractice.todoapp.Database.MyDatabase
import com.ndPractice.todoapp.add_task_bottom_sheet.AddTaskFragment
import com.ndPractice.todoapp.databinding.FragmentListBinding
import com.ndPractice.todoapp.utils.DayViewContainer
import main.fragments.list_fragment.adapter.TaskAdapter
import java.time.LocalDate
import java.time.YearMonth


class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val adapter = TaskAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tasksRecyclerView.adapter = adapter
        refreshTasks()

        parentFragmentManager.setFragmentResultListener(
            AddTaskFragment.REQUEST_KEY_TASK_ADDED,
            viewLifecycleOwner,
        ) { _, _ ->
            refreshTasks()
        }
        initcalendar()
    }

    private fun refreshTasks() {
        val tasks = MyDatabase.getDatabase().taskDao().getAllTasks()
        adapter.updateTasks(tasks)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initcalendar() {
        binding.calendarView.dayBinder = object : WeekDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: WeekDay) {
                container.textView.text = data.date.dayOfMonth.toString()
            }
        }
        val currentDate = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startDate = currentMonth.minusMonths(12).atStartOfMonth()
        val endDate = currentMonth.plusMonths(12).atEndOfMonth()
        val firstDayOfWeek = firstDayOfWeekFromLocale()
        binding.calendarView.setup(startDate, endDate, firstDayOfWeek)
        binding.calendarView.scrollToWeek(currentDate)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}