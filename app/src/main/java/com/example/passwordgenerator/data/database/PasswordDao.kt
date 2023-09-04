package com.example.passwordgenerator.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.passwordgenerator.data.database.entities.FolderEntity
import com.example.passwordgenerator.data.database.entities.FolderWithPasswords
import com.example.passwordgenerator.data.database.entities.PasswordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePassword(password: PasswordEntity)

    @Query("SELECT * FROM passwords WHERE folderName IS NULL")
    fun getNoFolderPasswords(): Flow<List<PasswordEntity>>

    @Delete
    suspend fun deletePassword(password: PasswordEntity)

    @Insert
    suspend fun addFolder(folderEntity: FolderEntity)

    @Query("SELECT * FROM folders")
    fun getFolders(): Flow<List<FolderEntity>>

    @Transaction
    @Query("SELECT * FROM folders")
    fun getFoldersWithPasswords(): Flow<List<FolderWithPasswords>>
}