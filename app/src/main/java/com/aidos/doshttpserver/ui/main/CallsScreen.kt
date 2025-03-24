package com.aidos.doshttpserver.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aidos.doshttpserver.R
import com.aidos.doshttpserver.ui.main.viewstate.CallItem
import com.aidos.doshttpserver.ui.theme.DosHttpServerTheme

@Composable
fun CallsScreen(
    callItems: List<CallItem>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 24.dp),
            style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            text = stringResource(id = R.string.title_calls)
        )

        callItems.forEach {
            CallItemCard(callItem = it)
        }
    }
}

@Composable
fun CallItemCard(callItem: CallItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = callItem.name,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = callItem.duration,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )
    }
}

@Preview
@Composable
fun PreviewCallsScreen() {
    val callItems = buildList {
        repeat(20) {
            add(CallItem("5 min", "John Thompson"))
        }
    }
    DosHttpServerTheme {
        CallsScreen(callItems = callItems)
    }
}