package com.example.stundensmanager.viewadaper

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stundensmanager.R
import com.example.stundensmanager.StudentDetailsActivity
import com.example.stundensmanager.enums.StudentDeatilsMode
import com.example.stundensmanager.models.StudentModel

class StudentsAdapter(private val students: List<StudentModel>) :
    RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {
    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.student_name)
        val idTextView: TextView = itemView.findViewById(R.id.student_id)
        val profilePicture: ImageView = itemView.findViewById(R.id.student_image)
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
        this.loadStudentImage(currentStudent, holder.profilePicture)

        holder.itemView.setOnClickListener {
            Intent(holder.itemView.context, StudentDetailsActivity::class.java).apply {
                putExtra("student", currentStudent)
                putExtra("mode", StudentDeatilsMode.VIEW)
                holder.itemView.context.startActivity(this)
            }
        }
    }

    private fun loadStudentImage(currentStudent: StudentModel, profilePictureView: ImageView) {
        profilePictureView.setImageResource(R.drawable.empty_profile_picture)
    }

    override fun getItemCount(): Int {
        return students.size
    }
}