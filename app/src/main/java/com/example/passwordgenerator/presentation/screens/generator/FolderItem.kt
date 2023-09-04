package com.example.passwordgenerator.presentation.screens.generator

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.passwordgenerator.R
import com.example.passwordgenerator.presentation.common_composables.CustomCard

@Composable
fun FolderItem(
    name: String,
    onClick: (String) -> Unit
) {
    CustomCard(modifier = Modifier.clickable {
        onClick(name)
    }) {
        Text(text = name)
        Icon(
            painter = painterResource(id = R.drawable.baseline_folder_24),
            contentDescription = stringResource(id = R.string.folder)
        )
    }
}