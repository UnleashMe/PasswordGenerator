package com.example.passwordgenerator.presentation.screens.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordgenerator.domain.model.Folder
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repositories.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordListScreenViewModel @Inject constructor(private val repository: PasswordRepository) :
    ViewModel() {

    private val _state = mutableStateOf(PasswordListScreenState())
    val state: State<PasswordListScreenState> = _state

    init {
        viewModelScope.launch {
            val passwords = repository.getNoFolderPasswords()
            val folders = repository.getFoldersWithPasswords()
            _state.value = state.value.copy(
                passwords = passwords,
                folders = folders.map { folderWithPasswords ->
                    folderWithPasswords.map { folderWithPasswords1 ->
                        Folder(
                            name = folderWithPasswords1.folder.name,
                            passwords = folderWithPasswords1.passwords.map { it.toPassword() })
                    }
                })
        }
    }

    fun onEvent(event: PasswordListEvent) {
        when (event) {
            is PasswordListEvent.DeletePassword -> {
                deletePassword(event.password)
            }

            is PasswordListEvent.OnFolderArrowClick -> {
                onFolderArrowClick(event.folder)
            }
        }
    }

    private fun deletePassword(password: Password) = viewModelScope.launch {
        repository.deletePassword(password = password)
    }

    private fun onFolderArrowClick(folder: Folder) {
        if (folder.isOpen) {
            _state.value =
                state.value.copy(folders = state.value.folders.map { folders ->
                    folders.map {
                        it.copy(
                            isOpen = false
                        )
                    }
                })
        } else {
            _state.value =
                state.value.copy(folders = state.value.folders.map { folders ->
                    folders.map {
                        if (it.name == folder.name) {
                            it.copy(isOpen = true)
                        } else it.copy(
                            isOpen = false
                        )
                    }
                })
        }
    }
}