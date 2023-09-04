package com.example.passwordgenerator.presentation.screens.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.passwordgenerator.R
import com.example.passwordgenerator.domain.model.Folder
import com.example.passwordgenerator.ui.theme.AppTheme

@Composable
fun FolderItem(
    folder: Folder,
    onExpandClick: (Folder) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.dimens.small)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(AppTheme.dimens.smallMedium)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_folder_24),
                contentDescription = stringResource(id = R.string.folder),
                modifier = Modifier
                    .padding(end = AppTheme.dimens.smallMedium)
                    .weight(1f)
            )
            Text(
                text = stringResource(
                    id = R.string.folder_info,
                    folder.name,
                    folder.passwords.size
                ),
                modifier = Modifier.weight(7f)
            )
            IconButton(onClick = {
                onExpandClick(folder)
            }, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = if (folder.isOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(id = R.string.expand_button)
                )
            }
        }
    }
}