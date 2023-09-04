package com.example.passwordgenerator.data.repositories

import com.example.passwordgenerator.data.database.PasswordDao
import com.example.passwordgenerator.data.database.entities.FolderEntity
import com.example.passwordgenerator.data.database.entities.FolderWithPasswords
import com.example.passwordgenerator.data.database.entities.toPasswordEntity
import com.example.passwordgenerator.di.IoDispatcher
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repositories.PasswordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val passwordDao: PasswordDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : PasswordRepository {

    override suspend fun savePassword(password: Password) = withContext(ioDispatcher) {
        passwordDao.savePassword(password.toPasswordEntity())
    }

    override fun getNoFolderPasswords(): Flow<List<Password>> {
        return passwordDao.getNoFolderPasswords()
            .map { passwordEntities -> passwordEntities.map { it.toPassword() } }
            .flowOn(ioDispatcher)
    }

    override suspend fun deletePassword(password: Password) = withContext(ioDispatcher) {
        passwordDao.deletePassword(password.toPasswordEntity())
    }

    override fun getFolderNames(): Flow<List<String>> {
        return passwordDao.getFolders().map { folderEntities -> folderEntities.map { it.name } }
            .flowOn(ioDispatcher)
    }

    override suspend fun addFolder(name: String) = withContext(ioDispatcher) {
        passwordDao.addFolder(FolderEntity(name))
    }

    override fun getFoldersWithPasswords(): Flow<List<FolderWithPasswords>> {
        return passwordDao.getFoldersWithPasswords().flowOn(ioDispatcher)
    }
}