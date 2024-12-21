package com.example.stundensmanager

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stundensmanager.enums.StudentDeatilsMode
import com.example.stundensmanager.models.StudentsDataHolder

class StudentDetailsActivity : AppCompatActivity() {
    private val studentIndex by lazy { intent.getIntExtra("student_index", -1) }
    private val currentStudent by lazy { StudentsDataHolder.getStudentByIndex(studentIndex) }
    private val idTextView by lazy { findViewById<TextView>(R.id.save_student_id) }
    private val nameTextView by lazy { findViewById<TextView>(R.id.save_student_name) }
    private val phoneTextView by lazy { findViewById<TextView>(R.id.save_student_phone) }
    private val addressTextView by lazy { findViewById<TextView>(R.id.student_details_address) }
    private val checkBox by lazy { findViewById<CheckBox>(R.id.student_details_checkbox) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        currentStudent?.run {
            idTextView.text = id.toString()
            nameTextView.text = name
            phoneTextView.text = phone
            addressTextView.text = email
            checkBox.isChecked = checked
        }
        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.edit_button).setOnClickListener {
            startActivity(Intent(this, SaveStudentActivity::class.java).apply {
                putExtra("student_index", studentIndex)
                putExtra("mode", StudentDeatilsMode.EDIT)
            })
            finish()
        }
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}