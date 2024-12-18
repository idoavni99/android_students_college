package com.example.stundensmanager.models

import com.example.stundensmanager.enums.StudentsListChange

object StudentsDataHolder {
    private val studentsData = mutableListOf(
        StudentModel("Ido Avni", 318800349),
        StudentModel("Yonatan Yakub", 231787334)
    )
    var lastDiff: Pair<StudentsListChange, Int>? = null

    fun addStudent(student: StudentModel) {
        studentsData.add(student)
        lastDiff = Pair(StudentsListChange.INSERTED, studentsData.size - 1)
    }

    fun editStudent(student: StudentModel, index: Int? = null) {
        val currIndex = index ?: studentsData.indexOfFirst { it.id == student.id }
        if (currIndex != -1) {
            studentsData[currIndex] = student
            lastDiff = Pair(StudentsListChange.EDITED, currIndex)
        }
    }

    fun deleteStudentByIndex(index: Int) {
        studentsData.removeAt(index)
        lastDiff = Pair(StudentsListChange.DELETED, index)
    }

    fun setDiffCommited() {
        lastDiff = null
    }

    fun getStudentByIndex(index: Int) = studentsData.getOrNull(index)

    fun getStudentsList(): List<StudentModel> = studentsData
}