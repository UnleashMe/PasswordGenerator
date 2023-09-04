package com.example.passwordgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.passwordgenerator.presentation.navigation.Navigation
import com.example.passwordgenerator.ui.theme.PasswordGeneratorTheme
import com.example.passwordgenerator.ui.theme.rememberWindowSizeClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val windowSize = rememberWindowSizeClass()
            PasswordGeneratorTheme(windowSize) {
                Navigation()
            }
        }
    }
}

