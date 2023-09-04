package com.example.passwordgenerator.domain.model

data class Folder(
    val name: String,
    val passwords: List<Password>,
    val isOpen: Boolean = false
)