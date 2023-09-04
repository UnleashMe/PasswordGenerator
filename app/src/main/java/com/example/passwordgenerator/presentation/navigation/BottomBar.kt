package com.example.passwordgenerator.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavController, onItemClick: (Screen) -> Unit) {

    val screens = listOf(Screen.GeneratorScreen, Screen.ListScreen)

    val navBackstackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackstackEntry?.destination

    NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        screens.forEach {
            NavigationBarItem(
                selected = it.route == currentDestination?.route,
                onClick = { onItemClick(it) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = it.icon, contentDescription = it.title)
                        Text(text = it.title, style = MaterialTheme.typography.bodySmall)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.tertiary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}