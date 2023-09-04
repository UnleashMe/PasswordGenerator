package com.example.passwordgenerator.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object GeneratorScreen : Screen("generator", "generate", Icons.Default.Add)
    object ListScreen : Screen("list", "passwords", Icons.Default.List)
}
