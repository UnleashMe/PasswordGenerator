package com.example.passwordgenerator.presentation.common_composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.passwordgenerator.ui.theme.AppTheme

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = AppTheme.dimens.smallMedium, vertical = AppTheme.dimens.small)
            .then(modifier),
        elevation = CardDefaults.cardElevation(defaultElevation = AppTheme.dimens.small),
        border = BorderStroke(AppTheme.dimens.wee, MaterialTheme.colorScheme.tertiary)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = AppTheme.dimens.smallMedium, vertical = AppTheme.dimens.small)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            content()
        }
    }
}