package com.example.passwordgenerator.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.passwordgenerator.presentation.screens.generator.GeneratorScreen
import com.example.passwordgenerator.presentation.screens.list.PasswordListScreen

@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = Screen.GeneratorScreen.route) {
        composable(route = Screen.GeneratorScreen.route) {
            GeneratorScreen(paddingValues = paddingValues)
        }
        composable(route = Screen.ListScreen.route) {
            PasswordListScreen(paddingValues = paddingValues)
        }
    }
}