package com.tareq.auth.presentation.register.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tareq.core.presentation.designsystem.CheckIcon
import com.tareq.core.presentation.designsystem.CrossIcon
import com.tareq.core.presentation.designsystem.StepTrackerDarkGray
import com.tareq.core.presentation.designsystem.StepTrackerTheme

@Composable
fun PasswordRequirement(
    modifier: Modifier = Modifier,
    text: String,
    isValid: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) CheckIcon else CrossIcon,
            contentDescription = "password requirement",
            tint = if (isValid) MaterialTheme.colorScheme.primary else StepTrackerDarkGray,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text, color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun PasswordRequirementInValidPreview() {
    StepTrackerTheme {
        PasswordRequirement(text = "Must be at least 8 characters", isValid = false)
    }
}

@Preview
@Composable
private fun PasswordRequirementValidPreview() {
    StepTrackerTheme {
        PasswordRequirement(text = "Must be at least 8 characters", isValid = true)
    }
}