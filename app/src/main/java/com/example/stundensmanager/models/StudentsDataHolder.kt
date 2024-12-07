package com.example.stundensmanager.models

class StudentsDataHolder {
    companion object {
        val studentsData = mutableListOf(
            StudentModel("Ido Avni", 318800349),
            StudentModel("Yonatan Yakub", 231787334)
        )

        fun addStudent(student: StudentModel) {
            studentsData.add(student)
        }

        fun editStudent(student: StudentModel, index: Int? = null) {
            val currIndex = index ?: studentsData.indexOfFirst { it.id == student.id }
            if (currIndex != -1) {
                studentsData[currIndex] = student
            }
        }

        fun deleteStudent(student: StudentModel) {
            studentsData.remove(student)
        }

        fun getStudentByIndex(index: Int) = studentsData.getOrNull(index);
    }
}