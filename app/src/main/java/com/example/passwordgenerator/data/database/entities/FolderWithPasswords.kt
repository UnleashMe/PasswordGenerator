package com.example.passwordgenerator.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class FolderWithPasswords(
    @Embedded
    val folder: FolderEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "folderName"
    )
    val passwords: List<PasswordEntity>
)
