package com.example.passwordgenerator.domain.model

data class Password(
    val password: String = "",
    val entropy: String = "",
    val symbols: String = "",
    val folderName: String? = null
)
