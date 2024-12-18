package com.example.stundensmanager

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.stundensmanager.enums.StudentDeatilsMode
import com.example.stundensmanager.models.StudentModel
import com.example.stundensmanager.models.StudentsDataHolder

class SaveStudentActivity : AppCompatActivity() {
    private val studentIndex by lazy {
        intent.getIntExtra("student_index", -1)
    }
    private val currentStudent: StudentModel? by lazy {
        StudentsDataHolder.getStudentByIndex(studentIndex)

    }
    private val currentMode: StudentDeatilsMode by lazy {
        intent.getSerializableExtra("mode") as StudentDeatilsMode
    }
    private val idTextView by lazy { findViewById<EditText>(R.id.save_student_id) }
    private val nameTextView by lazy { findViewById<EditText>(R.id.save_student_name) }
    private val phoneTextView by lazy { findViewById<EditText>(R.id.save_student_phone) }
    private val addressTextView by lazy { findViewById<EditText>(R.id.student_details_address) }
    private val checkBox by lazy { findViewById<CheckBox>(R.id.student_details_checkbox) }

    private val cancelButton by lazy { findViewById<Button>(R.id.details_cancel_button) }
    private val deleteButton by lazy { findViewById<Button>(R.id.details_delete_button) }
    private val saveButton by lazy { findViewById<Button>(R.id.details_save_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_save_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initToolbar()
        setupActions()
        setupInputFields()
        setToolbarTitle()
    }

    private fun setupInputFields(
    ) {
        idTextView.setText(currentStudent?.id?.toString() ?: "")
        nameTextView.setText(currentStudent?.name ?: "")
        phoneTextView.setText(currentStudent?.phone ?: "")
        addressTextView.setText(currentStudent?.email ?: "")
        checkBox.isChecked = currentStudent?.checked ?: false
    }

    private fun setupActions() {
        if(currentMode == StudentDeatilsMode.ADD){
            deleteButton.isVisible = false
        }

        deleteButton?.setOnClickListener {
            StudentsDataHolder.deleteStudentByIndex(studentIndex)
            finish()
        }
        saveButton?.setOnClickListener {
            val studentData = getStudentFromInputs()
            val studentValidationResult = studentData.validate()
            if (!studentValidationResult.success) {
                Toast.makeText(this, studentValidationResult.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when (currentMode) {
                StudentDeatilsMode.ADD -> {
                    StudentsDataHolder.addStudent(studentData)
                }

                StudentDeatilsMode.EDIT -> {
                    StudentsDataHolder.editStudent(studentData, studentIndex)
                }
            }

            finish()
        }
        cancelButton?.setOnClickListener {
            finish()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setToolbarTitle() {
        supportActionBar?.apply {
            title = when (currentMode) {
                StudentDeatilsMode.ADD -> getString(R.string.add_student_title)
                StudentDeatilsMode.EDIT -> getString(R.string.edit_student_title)
            }
        }
    }

    private fun getStudentFromInputs() = StudentModel(
        nameTextView.text.toString(),
        idTextView.text.toString().toIntOrNull() ?: 0,
        phoneTextView.text.toString(),
        addressTextView.text.toString(),
        checkBox.isChecked
    )

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}