package com.example.stundensmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stundensmanager.R
import com.example.stundensmanager.enums.StudentDetailsMode
import com.example.stundensmanager.models.StudentModel
import com.example.stundensmanager.models.StudentsDataHolder

class SaveStudentFragment : Fragment() {
    private val args by navArgs<SaveStudentFragmentArgs>()
    private val currentStudent: StudentModel? by lazy {
        StudentsDataHolder.getStudentByIndex(args.studentIndex)
    }
    private val idTextView by lazy { view?.findViewById<EditText>(R.id.save_student_id) }
    private val nameTextView by lazy { view?.findViewById<EditText>(R.id.save_student_name) }
    private val phoneTextView by lazy { view?.findViewById<EditText>(R.id.save_student_phone) }
    private val addressTextView by lazy { view?.findViewById<EditText>(R.id.student_details_address) }
    private val checkBox by lazy { view?.findViewById<CheckBox>(R.id.student_details_checkbox) }

    private val cancelButton by lazy { view?.findViewById<Button>(R.id.details_cancel_button) }
    private val deleteButton by lazy { view?.findViewById<Button>(R.id.details_delete_button) }
    private val saveButton by lazy { view?.findViewById<Button>(R.id.details_save_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInputFields()
        setupActions(view)
    }

    private fun setupInputFields(
    ) {
        currentStudent?.run {
            idTextView?.setText(id.toString())
            nameTextView?.setText(name)
            phoneTextView?.setText(phone)
            addressTextView?.setText(email)
            checkBox?.isChecked = checked
        }
    }

    private fun setupActions(view: View) {
        val nav = view.findNavController()
        if (args.mode == StudentDetailsMode.ADD) {
            deleteButton?.isVisible = false
        }

        deleteButton?.setOnClickListener {
            StudentsDataHolder.deleteStudentByIndex(args.studentIndex)
            nav.navigate(SaveStudentFragmentDirections.actionSaveStudentFragmentToStudentsListFragment())
        }
        saveButton?.setOnClickListener {
            val studentData = getStudentFromInputs()
            val studentValidationResult = studentData.validate()
            if (!studentValidationResult.success) {
                Toast.makeText(context, studentValidationResult.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when (args.mode) {
                StudentDetailsMode.ADD -> {
                    StudentsDataHolder.addStudent(studentData)
                }

                StudentDetailsMode.EDIT -> {
                    StudentsDataHolder.editStudent(studentData, args.studentIndex)
                }
            }
            nav.navigate(SaveStudentFragmentDirections.actionSaveStudentFragmentToStudentsListFragment())
        }
        cancelButton?.setOnClickListener {
            nav.navigate(SaveStudentFragmentDirections.actionSaveStudentFragmentToStudentsListFragment())
        }
    }

    private fun getStudentFromInputs() = StudentModel(
        nameTextView?.text.toString(),
        idTextView?.text.toString().toIntOrNull() ?: 0,
        phoneTextView?.text.toString(),
        addressTextView?.text.toString(),
        checkBox?.isChecked ?: false
    )
}