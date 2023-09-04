package com.example.passwordgenerator.presentation.screens.list

import com.example.passwordgenerator.domain.model.Folder
import com.example.passwordgenerator.domain.model.Password

sealed class PasswordListEvent {
    class DeletePassword(val password: Password): PasswordListEvent()
    class OnFolderArrowClick(val folder: Folder): PasswordListEvent()
}
