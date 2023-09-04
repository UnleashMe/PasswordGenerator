package com.example.passwordgenerator.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                onItemClick = { navController.navigate(it.route) })
        }
    ) {
        NavGraph(navController = navController, paddingValues = it)
    }
}