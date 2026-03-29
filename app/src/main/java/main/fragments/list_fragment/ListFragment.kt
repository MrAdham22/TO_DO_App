package main.fragments.list_fragment

import android.os.Build
import android.os.Bundle
import android.graphics.Canvas
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
    private val adapter = TaskAdapter(
        tasks = emptyList(),
        onDoneClick = ::onDoneClicked,
        onDeleteClick = ::onDeleteClicked,
        onItemClick = ::onTaskClicked
    )

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
        attachSwipeToRevealDelete()
        refreshTasks()

        parentFragmentManager.setFragmentResultListener(
            AddTaskFragment.REQUEST_KEY_TASK_ADDED,
            viewLifecycleOwner,
        ) { _, _ ->
            refreshTasks()
        }
        initcalendar()
    }

    private fun onDoneClicked(task: com.ndPractice.todoapp.Database.entities.Task) {
        MyDatabase.getDatabase().taskDao().updateTask(task.copy(isCompleted = !task.isCompleted))
        refreshTasks()
    }

    private fun onDeleteClicked(task: com.ndPractice.todoapp.Database.entities.Task) {
        MyDatabase.getDatabase().taskDao().deleteTask(task)
        refreshTasks()
    }

    private fun onTaskClicked(task: com.ndPractice.todoapp.Database.entities.Task) {
        AddTaskFragment.newInstance(task)
            .show(parentFragmentManager, AddTaskFragment::class.java.simpleName)
    }

    private fun refreshTasks() {
        val tasks = MyDatabase.getDatabase().taskDao().getAllTasks()
        adapter.updateTasks(tasks)
    }

    private fun attachSwipeToRevealDelete() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float = 2f

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    return
                }
                val holder = viewHolder as TaskAdapter.TaskViewHolder
                val revealWidth = adapter.getRevealWidthPx(recyclerView.resources.displayMetrics.density)
                val newTranslation = if (isCurrentlyActive) dX.coerceIn(0f, revealWidth) else holder.binding.foregroundContainer.translationX
                holder.binding.foregroundContainer.translationX = newTranslation
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                val holder = viewHolder as TaskAdapter.TaskViewHolder
                val position = viewHolder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) return
                val revealWidth = adapter.getRevealWidthPx(recyclerView.resources.displayMetrics.density)
                val task = adapter.getTaskAt(position)
                val shouldOpen = holder.binding.foregroundContainer.translationX > revealWidth / 2
                adapter.setSwipedTask(if (shouldOpen) task.id else null)
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.tasksRecyclerView)
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