package main.fragments.list_fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ndPractice.todoapp.R
import com.ndPractice.todoapp.Database.entities.Task
import com.ndPractice.todoapp.databinding.ItemTaskBinding

class TaskAdapter(
    private var tasks: List<Task>,
    private val onDoneClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit,
    private val onItemClick: (Task) -> Unit,
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var swipedTaskId: Int? = null
    private val revealWidthPx = 96f

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val task = tasks[position]
        holder.binding.itemTodoTitle.text = task.title
        holder.binding.itemTodoTime.text = task.description
        holder.binding.foregroundContainer.translationX =
            if (task.id == swipedTaskId) revealWidthPx * holder.binding.root.resources.displayMetrics.density else 0f

        holder.binding.icDone.visibility = if (task.isCompleted) View.GONE else View.VISIBLE
        holder.binding.doneText.visibility = if (task.isCompleted) View.VISIBLE else View.GONE
        val green = ContextCompat.getColor(holder.binding.root.context, R.color.green)
        val blue = ContextCompat.getColor(holder.binding.root.context, R.color.blue)
        val grey = ContextCompat.getColor(holder.binding.root.context, R.color.grey)
        val titleColor = if (task.isCompleted) green else blue
        val timeColor = if (task.isCompleted) green else grey
        val lineColor = if (task.isCompleted) green else blue
        holder.binding.itemTodoTitle.setTextColor(titleColor)
        holder.binding.itemTodoTime.setTextColor(timeColor)
        holder.binding.verticalView.setCardBackgroundColor(lineColor)
        holder.binding.verticalView.strokeColor = lineColor

        holder.binding.icDone.setOnClickListener {
            onDoneClick(task)
        }
        holder.binding.deleteAction.setOnClickListener {
            onDeleteClick(task)
        }
        holder.binding.foregroundContainer.setOnClickListener {
            if (task.id == swipedTaskId) {
                setSwipedTask(null)
            } else {
                onItemClick(task)
            }
        }
    }

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(newTasks: List<Task>) {
        this.tasks = newTasks
        notifyDataSetChanged()
    }

    fun getTaskAt(position: Int): Task = tasks[position]

    fun getRevealWidthPx(recyclerViewDensity: Float): Float = revealWidthPx * recyclerViewDensity

    fun setSwipedTask(taskId: Int?) {
        swipedTaskId = taskId
        notifyDataSetChanged()
    }

    fun isTaskSwiped(taskId: Int): Boolean = swipedTaskId == taskId

    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)
}