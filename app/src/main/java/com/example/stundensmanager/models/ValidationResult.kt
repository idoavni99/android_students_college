package com.example.stundensmanager.models

class ValidationResult(e: IllegalArgumentException? = null) {
    val success = e == null
    val message = e?.message
}
