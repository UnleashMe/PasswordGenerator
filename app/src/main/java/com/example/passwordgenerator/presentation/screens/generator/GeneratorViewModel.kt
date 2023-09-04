package com.example.passwordgenerator.presentation.screens.generator

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.domain.repositories.PasswordRepository
import com.example.passwordgenerator.util.Constants.MAX_FOLDER_NAME_LENGTH
import com.example.passwordgenerator.util.Constants.TOAST_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.math.log2
import kotlin.math.pow

@HiltViewModel
class GeneratorViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository
) : ViewModel() {

    private val _state = mutableStateOf(GeneratorScreenState())
    val state: State<GeneratorScreenState> = _state

    init {
        generatePassword()
    }

    fun onEvent(event: GeneratorScreenEvent) {
        when (event) {
            is GeneratorScreenEvent.GeneratePassword -> {
                generatePassword()
            }

            is GeneratorScreenEvent.ChangePasswordLength -> {
                changePasswordLength(event.length)
            }

            is GeneratorScreenEvent.ChangeUppercaseState -> {
                changeUppercaseState(event.enable)
            }

            is GeneratorScreenEvent.ChangeLowercaseState -> {
                changeLowercaseState(event.enable)
            }

            is GeneratorScreenEvent.ChangeNumbersState -> {
                changeNumbersState(event.enable)
            }

            is GeneratorScreenEvent.ChangeSpecialSymbolsState -> {
                changeSpecialState(event.enable)
            }

            is GeneratorScreenEvent.SavePassword -> {
                savePassword(event.folderName)
            }

            is GeneratorScreenEvent.OnDismissChooseFolderDialog -> {
                onDismissChooseFolderDialog()
            }

            is GeneratorScreenEvent.ShowChooseFolderDialog -> {
                showChooseFolderDialog()
            }

            is GeneratorScreenEvent.OnDismissCreateFolderDialog -> {
                dismissCreateFolderDialog()
            }

            is GeneratorScreenEvent.ShowCreateFolderDialog -> {
                showCreateFolderDialog()
            }

            is GeneratorScreenEvent.CreateFolder -> {
                createFolder(event.name)
            }

            is GeneratorScreenEvent.OnNewFolderNameInput -> {
                onNewFolderInput(event.name)
            }
        }
    }

    private fun generatePassword() {
        var password = ""
        val lowercase = ('a'..'z').toList()
        val uppercase = ('A'..'Z').toList()
        val numbers = ('0'..'9').toList()
        val specialSymbols = setOf('@', '!', '#', '&', '?', '%', '$')

        val symbols = mutableListOf<Char>()
        if (state.value.lowercaseEnabled) {
            symbols.addAll(lowercase)
        }
        if (state.value.uppercaseEnabled) {
            symbols.addAll(uppercase)
        }
        if (state.value.numbersEnabled) {
            symbols.addAll(numbers)
        }
        if (state.value.specialSymbolsEnabled) {
            symbols.addAll(specialSymbols)
        }
        if (symbols.isEmpty()) return
        val entropy = log2(symbols.count().toDouble().pow(state.value.length))

        while (password.length != state.value.length) {
            password += symbols.random()
        }

        viewModelScope.launch {
            passwordRepository.getFolderNames().collect {
                _state.value =
                    state.value.copy(
                        password = password,
                        entropy = DecimalFormat("#.##").format(entropy),
                        folderNames = it
                    )
            }
        }
    }

    private fun changePasswordLength(newLength: Int) {
        if (newLength != state.value.length) {
            _state.value = state.value.copy(length = newLength)
            generatePassword()
        }
    }

    private fun changeUppercaseState(enable: Boolean) {
        _state.value = state.value.copy(uppercaseEnabled = enable)
        generatePassword()
        enableButtons()
    }

    private fun changeLowercaseState(enable: Boolean) {
        _state.value = state.value.copy(lowercaseEnabled = enable)
        generatePassword()
        enableButtons()
    }

    private fun changeNumbersState(enable: Boolean) {
        _state.value = state.value.copy(numbersEnabled = enable)
        generatePassword()
        enableButtons()
    }

    private fun changeSpecialState(enable: Boolean) {
        _state.value = state.value.copy(specialSymbolsEnabled = enable)
        generatePassword()
        enableButtons()
    }

    private fun savePassword(folderName: String? = null) = viewModelScope.launch {
        val symbols = mutableListOf<String>()
        if (state.value.uppercaseEnabled) symbols.add("A-Z")
        if (state.value.lowercaseEnabled) symbols.add("a-z")
        if (state.value.numbersEnabled) symbols.add("0-9")
        if (state.value.specialSymbolsEnabled) symbols.add("!@#$")

        passwordRepository.savePassword(
            Password(
                password = state.value.password,
                entropy = state.value.entropy,
                symbols = symbols.joinToString(", "),
                folderName = folderName
            )
        )
        onDismissChooseFolderDialog()
        showPasswordSavedToast()
    }

    private fun onDismissChooseFolderDialog() {
        _state.value = state.value.copy(chooseFolderDialogShown = false)
    }

    private fun showChooseFolderDialog() {
        _state.value = state.value.copy(chooseFolderDialogShown = true)
    }

    private fun showCreateFolderDialog() {
        _state.value = state.value.copy(createFolderDialogShown = true)
    }

    private fun dismissCreateFolderDialog() {
        _state.value = state.value.copy(createFolderDialogShown = false, newFolderName = "")
    }

    private fun createFolder(name: String) = viewModelScope.launch {
        try {
            passwordRepository.addFolder(name)
            dismissCreateFolderDialog()
        } catch (e: SQLiteConstraintException) {
            _state.value = state.value.copy(showFolderExistsToast = true)
            delay(TOAST_DELAY)
            _state.value = state.value.copy(showFolderExistsToast = false)
        }
    }

    private fun onNewFolderInput(name: String) {
        if (name.length <= MAX_FOLDER_NAME_LENGTH) {
            _state.value = state.value.copy(newFolderName = name)
        }
    }

    private fun showPasswordSavedToast() = viewModelScope.launch {
        _state.value = state.value.copy(passwordSavedToast = true)
        delay(TOAST_DELAY)
        _state.value = state.value.copy(passwordSavedToast = false)
    }

    private fun enableButtons() {
        _state.value = state.value.copy(
            buttonsEnabled = state.value.uppercaseEnabled || state.value.lowercaseEnabled || state.value.numbersEnabled || state.value.specialSymbolsEnabled
        )
    }
}