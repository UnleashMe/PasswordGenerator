package com.example.passwordgenerator.presentation.screens.list

import com.example.passwordgenerator.domain.model.Folder
import com.example.passwordgenerator.domain.model.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class PasswordListScreenState(
    val passwords: Flow<List<Password>> = flowOf(),
    val folders: Flow<List<Folder>> = flowOf()
)
