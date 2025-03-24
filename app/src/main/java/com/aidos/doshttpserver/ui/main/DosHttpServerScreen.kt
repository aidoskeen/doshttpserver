package com.aidos.doshttpserver.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aidos.doshttpserver.R
import com.aidos.doshttpserver.ui.theme.DosHttpServerTheme
import com.aidos.doshttpserver.ui.theme.SoftGreen
import com.aidos.doshttpserver.ui.theme.SoftRed

@Composable
fun DosHttpServerScreen(
    serverAddress: String,
    isServerRunning: Boolean,
    onStartService: () -> Unit,
    onStopService: () -> Unit,
    onNavigateToCallLogs: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 14.dp),
            style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            text = stringResource(id = R.string.app_name)
        )

        ServerDetails(serverAddress = serverAddress, isServerRunning = isServerRunning)

        val buttonStyle = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
            ,
            colors = ButtonDefaults.buttonColors().copy(containerColor = SoftGreen),
            onClick = onStartService,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                style = buttonStyle,
                text = stringResource(id = R.string.button_text_start)
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors().copy(containerColor = SoftRed),
            onClick = onStopService,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                style = buttonStyle,
                text = stringResource(id = R.string.button_text_stop)
            )
        }

        HorizontalDivider()

        ForwardButton(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.button_view_call_logs)
        ) {
            onNavigateToCallLogs()
        }

        HorizontalDivider()
    }
}

@Composable
fun ServerDetails(
    serverAddress: String,
    isServerRunning: Boolean
) {
    val titleStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface)
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(
                id = if (isServerRunning)
                    R.string.text_server_is_running
                else
                    R.string.text_server_is_inactive
            ),
            style = titleStyle
        )

        HorizontalDivider(Modifier.padding(horizontal = 16.dp))

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.text_server_address),
                style = titleStyle
            )
            Text(
                modifier = Modifier,
                text = serverAddress,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}

@Composable
private fun ForwardButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .then(modifier)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface)
        )
        
        Icon(
            tint = MaterialTheme.colorScheme.onBackground,
            painter = painterResource(id = R.drawable.ic_forward),
            contentDescription = null
        )
    }
}

@Composable
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFFFFF)
fun PreviewDosHttpScreen() {
    DosHttpServerTheme {
        DosHttpServerScreen(
            serverAddress = "192.168.0.1:12345",
            isServerRunning = true,
            onStopService = { },
            onStartService = { },
            onNavigateToCallLogs = { }
        )
    }
}