package com.ndPractice.todoapp.add_task_bottom_sheet

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ndPractice.todoapp.Database.MyDatabase
import com.ndPractice.todoapp.Database.entities.Task
import com.ndPractice.todoapp.databinding.FragmentAddTaskBinding
import com.ndPractice.todoapp.utils.format


class AddTaskFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddTaskBinding
    val selectedDate = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectedDate.text = selectedDate.format()
        initlisteners()
    }

    private fun initlisteners() {
        binding.addTaskButton.setOnClickListener {
            if (!validate()) return@setOnClickListener
            val task = Task(
                title = binding.titleInputLayout.editText!!.text.toString(),
                description = binding.descriptionInputLayout.editText!!.text.toString(),
                date = selectedDate.timeInMillis,
                isCompleted = false

            )
            val tasksBeforeAdding = MyDatabase.getDatabase().taskDao().getAllTasks()
            MyDatabase.getDatabase().taskDao().addtask(task)
            val tasksAfterAdding = MyDatabase.getDatabase().taskDao().getAllTasks()
            println("tasks before adding = $tasksBeforeAdding")
            println("tasks after adding = $tasksAfterAdding")
        }

        binding.selectedDate.setOnClickListener {
            var picker = DatePickerDialog(requireContext(),object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(
                    p0: DatePicker?,
                    year: Int,
                    month: Int,
                    day0fMonth: Int
                ) {
                    binding.selectedDate.text = "$day0fMonth/${month+1}/$year"
                    selectedDate.set(Calendar.YEAR,year)
                    selectedDate.set(Calendar.MONTH,month)
                    selectedDate.set(Calendar.DAY_OF_MONTH,day0fMonth)

                }
            },selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH))
            picker.show()
        }
    }
    fun validate(): Boolean{
        var isvalid = true

        if (binding.titleInputLayout.editText!!.text.isEmpty()){
            binding.titleInputLayout.error = "please enter title"
            isvalid = false
        }else{
            binding.titleInputLayout.error = null
        }

        if (binding.descriptionInputLayout.editText!!.text.isEmpty()){
            binding.descriptionInputLayout.error = "please enter description"
            isvalid = false
        }else{
            binding.descriptionInputLayout.error = null
        }
        return true
        }

    }




