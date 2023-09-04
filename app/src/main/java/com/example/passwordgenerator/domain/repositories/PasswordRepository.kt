package com.example.passwordgenerator.domain.repositories

import com.example.passwordgenerator.data.database.entities.FolderWithPasswords
import com.example.passwordgenerator.domain.model.Password
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    suspend fun savePassword(password: Password)

    fun getNoFolderPasswords(): Flow<List<Password>>

    suspend fun deletePassword(password: Password)

    fun getFolderNames(): Flow<List<String>>

    suspend fun addFolder(name: String)

    fun getFoldersWithPasswords(): Flow<List<FolderWithPasswords>>
}