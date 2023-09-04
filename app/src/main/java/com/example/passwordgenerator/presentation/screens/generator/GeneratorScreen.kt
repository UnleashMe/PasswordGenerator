package com.example.passwordgenerator.presentation.screens.generator

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.passwordgenerator.R
import com.example.passwordgenerator.presentation.common_composables.CustomCard
import com.example.passwordgenerator.ui.theme.AppTheme
import com.example.passwordgenerator.util.Constants.TOAST_DELAY
import kotlinx.coroutines.delay

@Composable
fun GeneratorScreen(
    viewModel: GeneratorViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val state = viewModel.state.value

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var passwordCopiedToast by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = passwordCopiedToast) {
        if (passwordCopiedToast) {
            Toast.makeText(context, R.string.password_copied, Toast.LENGTH_SHORT).show()
        }
        delay(TOAST_DELAY)
        passwordCopiedToast = false
    }
    LaunchedEffect(key1 = state.passwordSavedToast) {
        if (state.passwordSavedToast) {
            Toast.makeText(context, R.string.password_saved, Toast.LENGTH_SHORT).show()
        }
    }


    Column(
        modifier = Modifier
            .padding(AppTheme.dimens.smallMedium)
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = stringResource(id = R.string.generated_password, state.entropy),
                modifier = Modifier.padding(bottom = AppTheme.dimens.smallMedium),
                style = MaterialTheme.typography.bodyLarge
            )
            CustomCard {
                Text(text = state.password, style = MaterialTheme.typography.bodySmall)
                IconButton(onClick = {
                    clipboardManager.setText(AnnotatedString(text = state.password))
                    passwordCopiedToast = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_content_copy_24),
                        contentDescription = stringResource(id = R.string.copy)
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.length, state.length),
                modifier = Modifier.padding(bottom = AppTheme.dimens.smallMedium),
                style = MaterialTheme.typography.bodyMedium
            )
            CustomCard {
                Text(
                    text = state.lowestSliderPoint.toString(),
                    modifier = Modifier.padding(end = AppTheme.dimens.smallMedium),
                    style = MaterialTheme.typography.bodyMedium
                )
                Slider(
                    value = state.length.toFloat(),
                    onValueChange = { viewModel.onEvent(GeneratorScreenEvent.ChangePasswordLength(it.toInt())) },
                    modifier = Modifier.weight(1f),
                    valueRange = state.lowestSliderPoint.toFloat()..state.highestSliderPoint.toFloat(),
                    colors = SliderDefaults.colors(inactiveTrackColor = Color.Gray),
                    enabled = state.buttonsEnabled
                )
                Text(
                    text = state.highestSliderPoint.toString(),
                    modifier = Modifier.padding(start = AppTheme.dimens.smallMedium),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = stringResource(id = R.string.settings),
                modifier = Modifier.padding(bottom = AppTheme.dimens.smallMedium),
                style = MaterialTheme.typography.bodyMedium
            )
            CustomCard {
                Text(
                    text = stringResource(id = R.string.uppercase),
                    style = MaterialTheme.typography.bodyMedium
                )
                Switch(
                    checked = state.uppercaseEnabled,
                    onCheckedChange = {
                        viewModel.onEvent(
                            GeneratorScreenEvent.ChangeUppercaseState(
                                it
                            )
                        )
                    },
                )
            }
            CustomCard {
                Text(
                    text = stringResource(id = R.string.lowercase),
                    style = MaterialTheme.typography.bodyMedium
                )
                Switch(
                    checked = state.lowercaseEnabled,
                    onCheckedChange = {
                        viewModel.onEvent(
                            GeneratorScreenEvent.ChangeLowercaseState(
                                it
                            )
                        )
                    },
                )
            }
            CustomCard {
                Text(
                    text = stringResource(id = R.string.numbers),
                    style = MaterialTheme.typography.bodyMedium
                )
                Switch(
                    checked = state.numbersEnabled,
                    onCheckedChange = { viewModel.onEvent(GeneratorScreenEvent.ChangeNumbersState(it)) },
                )
            }
            CustomCard {
                Text(
                    text = stringResource(id = R.string.special_symbols),
                    style = MaterialTheme.typography.bodyMedium
                )
                Switch(
                    checked = state.specialSymbolsEnabled,
                    onCheckedChange = {
                        viewModel.onEvent(
                            GeneratorScreenEvent.ChangeSpecialSymbolsState(
                                it
                            )
                        )
                    },
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimens.smallMedium)
        ) {
            Button(
                onClick = { viewModel.onEvent(GeneratorScreenEvent.GeneratePassword) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                enabled = state.buttonsEnabled,
                border = BorderStroke(AppTheme.dimens.wee, MaterialTheme.colorScheme.tertiary)
            ) {
                Text(text = stringResource(id = R.string.generate_password))
            }
            Spacer(modifier = Modifier.weight(0.2f))
            Button(
                onClick = { viewModel.onEvent(GeneratorScreenEvent.ShowChooseFolderDialog) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                enabled = state.buttonsEnabled,
                border = BorderStroke(AppTheme.dimens.wee, MaterialTheme.colorScheme.tertiary)
            ) {
                Text(text = stringResource(id = R.string.save_password))
            }
        }
    }
    if (state.chooseFolderDialogShown) {
        ChooseFolderDialog(
            folders = state.folderNames,
            createFolderWindowShown = state.createFolderDialogShown,
            showFolderExistsToast = state.showFolderExistsToast,
            newFolderName = state.newFolderName,
            onDismiss = {
                viewModel.onEvent(GeneratorScreenEvent.OnDismissChooseFolderDialog)
            },
            onNoFolderSave = {
                viewModel.onEvent(GeneratorScreenEvent.SavePassword())
            },
            onNewFolderClick = {
                viewModel.onEvent(GeneratorScreenEvent.ShowCreateFolderDialog)
            },
            onSaveToFolder = {
                viewModel.onEvent(GeneratorScreenEvent.SavePassword(it))
            },
            onCreateFolderDismiss = {
                viewModel.onEvent(GeneratorScreenEvent.OnDismissCreateFolderDialog)
            },
            onNewFolderInput = {
                viewModel.onEvent(GeneratorScreenEvent.OnNewFolderNameInput(it))
            }
        ) {
            viewModel.onEvent(GeneratorScreenEvent.CreateFolder(it))
        }
    }
}