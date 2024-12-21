package com.example.stundensmanager.viewadaper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.stundensmanager.R
import com.example.stundensmanager.fragments.StudentsListFragmentDirections
import com.example.stundensmanager.models.StudentModel
import com.example.stundensmanager.models.StudentsDataHolder

class StudentsAdapter(private val students: List<StudentModel>) :
    RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.student_name)
        val idTextView: TextView = itemView.findViewById(R.id.student_id)
        val checkbox: CheckBox = itemView.findViewById(R.id.student_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_item, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentStudent = students[position]
        holder.nameTextView.text = currentStudent.name
        holder.idTextView.text = currentStudent.id.toString()
        holder.checkbox.isChecked = currentStudent.checked

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(StudentsListFragmentDirections.actionStudentsListFragmentToStudentDetailsFragment(position))
        }

        holder.checkbox.setOnClickListener {
            StudentsDataHolder.editStudent(
                currentStudent.copy(checked = holder.checkbox.isChecked),
                position
            )
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }
}