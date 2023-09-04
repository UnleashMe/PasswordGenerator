package com.example.passwordgenerator.presentation.screens.generator

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.passwordgenerator.R
import com.example.passwordgenerator.presentation.common_composables.CustomCard
import com.example.passwordgenerator.ui.theme.AppTheme

@Composable
fun ChooseFolderDialog(
    folders: List<String>,
    createFolderWindowShown: Boolean,
    showFolderExistsToast: Boolean,
    newFolderName: String,
    onDismiss: () -> Unit,
    onNoFolderSave: () -> Unit,
    onNewFolderClick: () -> Unit,
    onSaveToFolder: (String) -> Unit,
    onCreateFolderDismiss: () -> Unit,
    onNewFolderInput: (String) -> Unit,
    createFolder: (String) -> Unit,
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = showFolderExistsToast) {
        if (showFolderExistsToast) {
            Toast.makeText(context, R.string.folder_already_exists_toast, Toast.LENGTH_SHORT)
                .show()
        }
    }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f),
            elevation = CardDefaults.cardElevation(AppTheme.dimens.small),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(AppTheme.dimens.medium),
            border = BorderStroke(AppTheme.dimens.wee, MaterialTheme.colorScheme.tertiary)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = AppTheme.dimens.medium)
            ) {
                Text(
                    text = stringResource(id = R.string.choose_a_folder),
                    style = MaterialTheme.typography.headlineMedium
                )
                LazyColumn {
                    items(folders) {
                        FolderItem(name = it, onClick = onSaveToFolder)
                    }
                    item {
                        CustomCard(modifier = Modifier.clickable { onNoFolderSave() }) {
                            Text(text = stringResource(id = R.string.no_folder))
                        }
                    }
                    item {
                        CustomCard(modifier = Modifier.clickable { onNewFolderClick() }) {
                            Text(text = stringResource(id = R.string.new_folder))
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_create_new_folder_24),
                                contentDescription = stringResource(id = R.string.create_new_folder)
                            )
                        }
                    }
                }
            }
        }
    }
    if (createFolderWindowShown) {
        Dialog(
            onDismissRequest = { onCreateFolderDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                border = BorderStroke(AppTheme.dimens.tiny, MaterialTheme.colorScheme.tertiary),
                elevation = CardDefaults.cardElevation(AppTheme.dimens.small)
            ) {
                Text(
                    text = stringResource(id = R.string.name_a_folder),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = AppTheme.dimens.medium)
                )
                OutlinedTextField(
                    value = newFolderName,
                    onValueChange = { onNewFolderInput(it) },
                    label = {
                        Text(
                            text = stringResource(id = R.string.folders_name)
                        )
                    },
                    modifier = Modifier
                        .padding(bottom = AppTheme.dimens.smallMedium)
                        .align(Alignment.CenterHorizontally),
                    singleLine = true
                )
                Button(
                    onClick = {
                        createFolder(newFolderName)
                    },
                    enabled = newFolderName.isNotEmpty(),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = AppTheme.dimens.smallMedium)
                ) {
                    Text(text = stringResource(id = R.string.create))
                }
            }
        }
    }
}