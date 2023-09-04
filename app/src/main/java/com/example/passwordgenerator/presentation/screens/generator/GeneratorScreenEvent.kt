package com.example.passwordgenerator.presentation.screens.generator

sealed class GeneratorScreenEvent {
    object GeneratePassword: GeneratorScreenEvent()
    class ChangePasswordLength(val length: Int): GeneratorScreenEvent()
    class ChangeUppercaseState(val enable: Boolean): GeneratorScreenEvent()
    class ChangeLowercaseState(val enable: Boolean): GeneratorScreenEvent()
    class ChangeNumbersState(val enable: Boolean): GeneratorScreenEvent()
    class ChangeSpecialSymbolsState(val enable: Boolean): GeneratorScreenEvent()
    class SavePassword(val folderName: String? = null): GeneratorScreenEvent()
    object OnDismissChooseFolderDialog: GeneratorScreenEvent()
    object ShowChooseFolderDialog: GeneratorScreenEvent()
    object ShowCreateFolderDialog: GeneratorScreenEvent()
    object OnDismissCreateFolderDialog: GeneratorScreenEvent()
    class CreateFolder(val name: String): GeneratorScreenEvent()
    class OnNewFolderNameInput(val name: String): GeneratorScreenEvent()
}
