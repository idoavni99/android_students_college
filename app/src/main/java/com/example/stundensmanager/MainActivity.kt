package com.example.stundensmanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stundensmanager.enums.StudentDeatilsMode
import com.example.stundensmanager.enums.StudentsListChange
import com.example.stundensmanager.models.StudentsDataHolder
import com.example.stundensmanager.viewadaper.StudentsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val studentsList by lazy { findViewById<RecyclerView>(R.id.students_list) }
    private val addStudentButton by lazy { findViewById<FloatingActionButton>(R.id.add_student) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initStudentsList()
        initFab()
        initToolbar()
    }

    override fun onResume() {
        super.onResume()
        studentsList?.adapter?.run {
            StudentsDataHolder.lastDiff?.let { (changeType, changedPosition) ->
                when (changeType) {
                    StudentsListChange.INSERTED -> {
                        notifyItemInserted(changedPosition)
                    }

                    StudentsListChange.EDITED -> {
                        notifyItemChanged(changedPosition)
                    }

                    StudentsListChange.DELETED -> {
                        notifyItemRemoved(changedPosition)
                    }
                }
                StudentsDataHolder.setDiffCommited()
            }
        }
    }

    private fun initStudentsList() {
        studentsList?.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = StudentsAdapter(StudentsDataHolder.getStudentsList())
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    private fun initFab() {
        addStudentButton?.let {
            it.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        SaveStudentActivity::class.java
                    ).apply { putExtra("mode", StudentDeatilsMode.ADD) })
            }
        }
    }

    private fun initToolbar() {
        toolbar?.let {
            setSupportActionBar(it)
            supportActionBar?.apply {
                title = "Students List"
            }
        }
    }
}