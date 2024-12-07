package com.example.stundensmanager

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stundensmanager.enums.StudentDeatilsMode
import com.example.stundensmanager.models.StudentModel

class StudentDetailsActivity : AppCompatActivity() {
    lateinit var currentStudent: StudentModel;
    lateinit var mode: StudentDeatilsMode;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBundleParams(intent.extras)
        enableEdgeToEdge()

        setContentView(R.layout.activity_student_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initToolbar()
    }

    private fun initBundleParams(bundle: Bundle?) {
        bundle?.let {
            it.get("mode")?.let {
                mode = it as StudentDeatilsMode
            }
            it.get("student")?.let {
                currentStudent = it as StudentModel
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = when (mode) {
                StudentDeatilsMode.ADD -> getString(R.string.add_student_title)
                StudentDeatilsMode.EDIT -> getString(R.string.edit_student_title)
                StudentDeatilsMode.VIEW -> getString(R.string.view_student_title)
            }
            setDisplayHomeAsUpEnabled(true)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}