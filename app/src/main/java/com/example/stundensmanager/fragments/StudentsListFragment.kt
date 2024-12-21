package com.example.stundensmanager.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stundensmanager.R
import com.example.stundensmanager.enums.StudentDetailsMode
import com.example.stundensmanager.enums.StudentsListChange
import com.example.stundensmanager.fragments.StudentsListFragmentDirections.Companion.actionStudentsListFragmentToSaveStudentFragment
import com.example.stundensmanager.fragments.StudentsListFragmentDirections.Companion.actionStudentsListFragmentToStudentDetailsFragment
import com.example.stundensmanager.models.StudentsDataHolder
import com.example.stundensmanager.viewadaper.StudentsAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class StudentsListFragment : Fragment() {
    private lateinit var studentsList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_students_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studentsList = view.findViewById(R.id.students_list)
        context?.let { initStudentsList(it) }
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

    private fun initStudentsList(context: Context) {
        studentsList?.run {
            layoutManager = LinearLayoutManager(context)
            adapter = StudentsAdapter(StudentsDataHolder.getStudentsList())
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.app_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_item_save_student).apply {
            title = "Add"
            setOnMenuItemClickListener {
                view?.findNavController()?.navigate(
                    actionStudentsListFragmentToSaveStudentFragment(
                        StudentDetailsMode.ADD,
                        -1
                    )
                )
                true
            }
        }
    }
}