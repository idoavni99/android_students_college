package com.example.stundensmanager.models

import java.io.Serializable

data class StudentModel(val name: String, val id: Int, val phone: String? = null, val email: String? = null): Serializable
