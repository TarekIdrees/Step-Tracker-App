package com.tareq.run.presentation.run_overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tareq.core.presentation.designsystem.StepTrackerTheme
import com.tareq.core.presentation.designsystem.R


@Composable
internal fun EmptyRunsPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_runs_placholder),
            contentDescription = "empty runs placeholder",
            modifier = Modifier
                .size(250.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.empty_runs_start_new_run),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun EmptyRunsPlaceholderPreview() {
    StepTrackerTheme {
        EmptyRunsPlaceholder()
    }
}