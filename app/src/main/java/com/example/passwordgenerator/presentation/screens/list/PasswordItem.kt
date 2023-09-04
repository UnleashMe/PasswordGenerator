package com.example.passwordgenerator.presentation.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.passwordgenerator.R
import com.example.passwordgenerator.domain.model.Password
import com.example.passwordgenerator.presentation.common_composables.CustomCard

@Composable
fun PasswordItem(
    password: Password,
    onCopyClick: (String) -> Unit,
    onDeleteClick: (Password) -> Unit,
    modifier: Modifier = Modifier
) {
    CustomCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .weight(8f)
        ) {
            Text(
                text = password.password,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.entropy, password.entropy),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(id = R.string.symbols, password.symbols),
                style = MaterialTheme.typography.bodySmall
            )
        }
        IconButton(onClick = {
            onCopyClick(password.password)
        }, modifier = Modifier.weight(1f)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_content_copy_24),
                contentDescription = stringResource(id = R.string.copy)
            )
        }
        IconButton(onClick = { onDeleteClick(password) }, modifier = Modifier.weight(1f)) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(id = R.string.delete))
        }
    }
}