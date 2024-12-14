package com.example.stundensmanager

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stundensmanager.enums.StudentDeatilsMode
import com.example.stundensmanager.models.StudentModel
import com.example.stundensmanager.models.StudentsDataHolder

class StudentDetailsActivity : AppCompatActivity() {
    private val studentIndex by lazy {
        intent.getIntExtra("student_index", -1)
    }
    private val currentStudent: StudentModel? by lazy {
        StudentsDataHolder.getStudentByIndex(studentIndex)

    }
    private lateinit var currentMode: StudentDeatilsMode
    private val idTextView by lazy { findViewById<EditText>(R.id.student_details_id) }
    private val nameTextView by lazy { findViewById<EditText>(R.id.student_details_name) }
    private val phoneTextView by lazy { findViewById<EditText>(R.id.student_details_phone) }
    private val addressTextView by lazy { findViewById<EditText>(R.id.student_details_address) }
    private val checkBox by lazy { findViewById<CheckBox>(R.id.details_checkbox) }

    private val actionsBar by lazy { findViewById<View>(R.id.edit_student_actions) }
    private val editButton by lazy { findViewById<Button>(R.id.details_edit_button) }
    private val cancelButton by lazy { findViewById<Button>(R.id.details_cancel_button) }
    private val deleteButton by lazy { findViewById<Button>(R.id.details_delete_button) }
    private val saveButton by lazy { findViewById<Button>(R.id.details_save_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_student_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initToolbar()
        handleModeSwitch(intent.getSerializableExtra("mode") as StudentDeatilsMode)
        setupActions()
    }

    private fun setupInputFields(
        studentData: StudentModel? = null,
        currentMode: StudentDeatilsMode
    ) {
        idTextView.setText(studentData?.id?.toString() ?: "")
        nameTextView.setText(studentData?.name ?: "")
        phoneTextView.setText(studentData?.phone ?: "")
        addressTextView.setText(studentData?.email ?: "")
        checkBox.isChecked = studentData?.checked ?: false
        val focusable =
            currentMode != StudentDeatilsMode.VIEW

        idTextView.isFocusableInTouchMode = focusable
        nameTextView.isFocusableInTouchMode = focusable
        phoneTextView.isFocusableInTouchMode = focusable
        addressTextView.isFocusableInTouchMode = focusable

        idTextView.isFocusable = focusable
        nameTextView.isFocusable = focusable
        phoneTextView.isFocusable = focusable
        addressTextView.isFocusable = focusable
    }

    private fun setupActions() {
        editButton?.setOnClickListener {
            handleModeSwitch(StudentDeatilsMode.EDIT)
        }
        cancelButton?.setOnClickListener {
            currentStudent?.run {
                handleModeSwitch(StudentDeatilsMode.VIEW)
            } ?: finish()
        }
        deleteButton?.setOnClickListener {
            StudentsDataHolder.deleteStudentByIndex(studentIndex)
            finish()
        }
        saveButton?.setOnClickListener {
            val studentData = getStudentFromInputs()
            if (!studentData.validate()) {
                return@setOnClickListener
            }

            when (currentMode) {
                StudentDeatilsMode.ADD -> {
                    StudentsDataHolder.addStudent(studentData)
                }

                StudentDeatilsMode.EDIT -> {
                    StudentsDataHolder.editStudent(studentData, studentIndex)
                }

                else -> return@setOnClickListener
            }

            finish()
        }
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setToolbarTitle(newMode: StudentDeatilsMode) {
        supportActionBar?.apply {
            title = when (newMode) {
                StudentDeatilsMode.ADD -> getString(R.string.add_student_title)
                StudentDeatilsMode.EDIT -> getString(R.string.edit_student_title)
                StudentDeatilsMode.VIEW -> getString(R.string.view_student_title)
            }
        }
    }

    private fun handleModeSwitch(newMode: StudentDeatilsMode) {
        currentMode = newMode
        setToolbarTitle(newMode)

        when (newMode) {
            StudentDeatilsMode.VIEW -> {
                findViewById<View>(R.id.edit_student_actions)?.apply { visibility = View.GONE }
                editButton.apply { visibility = View.VISIBLE }
            }

            else -> {
                actionsBar?.apply { visibility = View.VISIBLE }
                deleteButton.apply {
                    visibility = if (newMode == StudentDeatilsMode.ADD) View.GONE else View.VISIBLE
                }
                editButton.apply { visibility = View.GONE }
            }
        }
        setupInputFields(currentStudent, currentMode)
    }

    private fun getStudentFromInputs() = StudentModel(
        nameTextView.text.toString(),
        Integer.parseInt(if (idTextView.text.isNullOrEmpty()) "0" else idTextView.text.toString()),
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