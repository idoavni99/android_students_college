package com.example.stundensmanager.models

import java.io.Serializable

data class StudentModel(
    val name: String,
    val id: Int,
    val phone: String? = null,
    val email: String? = null,
    val checked: Boolean = false
) {
    fun validate(): ValidationResult {
        try {
            require(name.isNotEmpty()) { "Name cannot be empty" }
            require(id > 0) { "ID must be a positive integer" }
            return ValidationResult()
        } catch (e: IllegalArgumentException) {
            return ValidationResult(e)
        }
    }
}
