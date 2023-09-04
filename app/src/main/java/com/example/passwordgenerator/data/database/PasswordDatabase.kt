package com.example.passwordgenerator.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passwordgenerator.data.database.entities.FolderEntity
import com.example.passwordgenerator.data.database.entities.PasswordEntity

@Database(entities = [PasswordEntity::class, FolderEntity::class], version = 1)
abstract class PasswordDatabase : RoomDatabase() {

    abstract fun getPasswordDao(): PasswordDao
}