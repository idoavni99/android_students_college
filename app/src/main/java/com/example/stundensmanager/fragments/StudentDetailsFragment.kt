package com.example.stundensmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stundensmanager.R
import com.example.stundensmanager.enums.StudentDetailsMode
import com.example.stundensmanager.fragments.StudentDetailsFragmentDirections.Companion.actionStudentDetailsFragmentToSaveStudentFragment
import com.example.stundensmanager.models.StudentsDataHolder


class StudentDetailsFragment : Fragment() {
    private val args by navArgs<StudentDetailsFragmentArgs>()
    private val currentStudent by lazy { StudentsDataHolder.getStudentByIndex(args.studentIndex) }
    private val idTextView by lazy { view?.findViewById<TextView>(R.id.save_student_id) }
    private val nameTextView by lazy { view?.findViewById<TextView>(R.id.save_student_name) }
    private val phoneTextView by lazy { view?.findViewById<TextView>(R.id.save_student_phone) }
    private val addressTextView by lazy { view?.findViewById<TextView>(R.id.student_details_address) }
    private val checkBox by lazy { view?.findViewById<CheckBox>(R.id.student_details_checkbox) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentStudent?.run {
            idTextView?.text = id.toString()
            nameTextView?.text = name
            phoneTextView?.text = phone
            addressTextView?.text = email
            checkBox?.isChecked = checked
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.app_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_item_save_student).apply {
            title = "Edit"
            setOnMenuItemClickListener {
                view?.findNavController()?.navigate(
                    actionStudentDetailsFragmentToSaveStudentFragment(
                        StudentDetailsMode.EDIT,
                        args.studentIndex
                    )
                )
                true
            }
        }
    }
}