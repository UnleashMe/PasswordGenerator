package com.example.passwordgenerator.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.passwordgenerator.domain.model.Password

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey
    val id: String,
    val password: String,
    val entropy: String,
    val symbols: String,
    val folderName: String?
) {
    fun toPassword(): Password {
        return Password(
            password, entropy, symbols, folderName
        )
    }
}

fun Password.toPasswordEntity(): PasswordEntity {
    return PasswordEntity(
        password + folderName, password, entropy, symbols, folderName
    )
}
