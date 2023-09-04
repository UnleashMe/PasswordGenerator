package com.example.passwordgenerator.presentation.screens.list

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passwordgenerator.R
import com.example.passwordgenerator.ui.theme.AppTheme
import com.example.passwordgenerator.util.Constants.TOAST_DELAY
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordListScreen(
    viewModel: PasswordListScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val state = viewModel.state.value
    val passwords = state.passwords.collectAsState(initial = listOf()).value
    val folders =
        state.folders.collectAsState(initial = listOf()).value.filter { it.passwords.isNotEmpty() }

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    var copyToast by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = copyToast) {
        if (copyToast) {
            Toast.makeText(context, R.string.password_copied, Toast.LENGTH_SHORT).show()
            delay(TOAST_DELAY)
            copyToast = false
        }
    }
    var deleteToast by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = deleteToast) {
        if (deleteToast) {
            Toast.makeText(
                context, R.string.password_deleted, Toast.LENGTH_SHORT
            ).show()
            delay(TOAST_DELAY)
            deleteToast = false
        }
    }

    if (folders.isEmpty() && passwords.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(id = R.string.empty_list_text),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            if (folders.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.saved_passwords),
                        modifier = Modifier.padding(AppTheme.dimens.smallMedium),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                folders.forEach { folder ->
                    item {
                        FolderItem(folder = folder) {
                            viewModel.onEvent(PasswordListEvent.OnFolderArrowClick(it))
                        }
                    }
                    item {
                        AnimatedVisibility(visible = folder.isOpen) {
                            Column {
                                folder.passwords.forEach { password ->
                                    PasswordItem(
                                        password = password,
                                        onCopyClick = {
                                            clipboardManager.setText(
                                                AnnotatedString(it)
                                            )
                                            copyToast = true
                                        },
                                        onDeleteClick = {
                                            viewModel.onEvent(
                                                PasswordListEvent.DeletePassword(
                                                    it
                                                )
                                            )
                                        },
                                        modifier = Modifier.animateItemPlacement()
                                    )
                                }
                            }
                        }
                    }
                }
            }
            if (passwords.isNotEmpty()) {
                item(key = 0) {
                    Text(
                        text = stringResource(id = R.string.no_folder_passwords),
                        modifier = Modifier
                            .padding(AppTheme.dimens.smallMedium)
                            .animateItemPlacement(),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                items(passwords, key = { it.password }) { password ->
                    PasswordItem(password = password, onCopyClick = {
                        clipboardManager.setText(AnnotatedString(it))
                        copyToast = true
                    }, onDeleteClick = {
                        viewModel.onEvent(PasswordListEvent.DeletePassword(it))
                        deleteToast = true
                    }, modifier = Modifier.animateItemPlacement())
                }
            }
        }
    }
}