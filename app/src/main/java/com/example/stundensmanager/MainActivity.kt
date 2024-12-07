package com.example.stundensmanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stundensmanager.enums.StudentDeatilsMode
import com.example.stundensmanager.models.StudentModel
import com.example.stundensmanager.viewadaper.StudentsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val students =
        listOf(StudentModel("Ido Avni", 318800349), StudentModel("Yonatan Yakub", 231787334))

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

    private fun initStudentsList() {
        findViewById<RecyclerView>(R.id.students_list)?.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = StudentsAdapter(students)
            it.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        }
    }

    private fun initFab() {
        findViewById<FloatingActionButton>(R.id.add_student)?.let {
            it.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        StudentDetailsActivity::class.java
                    ).apply { putExtra("mode", StudentDeatilsMode.ADD) })
            }
        }
    }
    private fun initToolbar() {
        findViewById<Toolbar>(R.id.toolbar)?.let {
            setSupportActionBar(it)
            supportActionBar?.apply {
             title = "Students List"
            }
        }
    }
}