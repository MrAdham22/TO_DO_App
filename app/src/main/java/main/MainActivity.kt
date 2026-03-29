package main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ndPractice.todoapp.R
import com.ndPractice.todoapp.add_task_bottom_sheet.AddTaskFragment
import com.ndPractice.todoapp.databinding.ActivityMainBinding
import main.fragments.list_fragment.ListFragment
import main.fragments.settings_fragment.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initlisteners()
        showFragment(ListFragment())
    }

    private fun initlisteners() {
        binding.fab.setOnClickListener {
            AddTaskFragment().show(supportFragmentManager, AddTaskFragment::class.java.simpleName)
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.listTab -> {
                    showFragment(ListFragment())

                }
                R.id.settingsTab -> {
                    showFragment(SettingsFragment())
                }
                else -> false
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, null)
            .commit()
    }
}