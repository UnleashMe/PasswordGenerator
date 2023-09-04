package com.example.passwordgenerator.presentation.screens.generator

import com.example.passwordgenerator.util.Constants.DEFAULT_PASSWORD_LENGTH
import com.example.passwordgenerator.util.Constants.HIGHEST_SLIDER_POINT
import com.example.passwordgenerator.util.Constants.LOWEST_SLIDER_POINT

data class GeneratorScreenState(
    val password: String = "",
    val entropy: String = "",
    val length: Int = DEFAULT_PASSWORD_LENGTH,
    val lowestSliderPoint: Int = LOWEST_SLIDER_POINT,
    val highestSliderPoint: Int = HIGHEST_SLIDER_POINT,
    val uppercaseEnabled: Boolean = true,
    val lowercaseEnabled: Boolean = true,
    val numbersEnabled: Boolean = true,
    val specialSymbolsEnabled: Boolean = true,
    val buttonsEnabled: Boolean = true,
    val chooseFolderDialogShown: Boolean = false,
    val folderNames: List<String> = listOf(),
    val createFolderDialogShown: Boolean = false,
    val showFolderExistsToast: Boolean = false,
    val newFolderName: String = "",
    val passwordSavedToast: Boolean = false
)
