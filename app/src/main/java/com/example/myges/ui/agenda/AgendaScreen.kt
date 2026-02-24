package com.example.myges.ui.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myges.R
import com.example.myges.core.domain.model.AgendaEvent
import com.example.myges.ui.components.ErrorContent
import com.example.myges.ui.components.LoadingContent
import java.text.DateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(viewModel: AgendaViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.nav_agenda)) })
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is AgendaUiState.Loading -> LoadingContent(modifier = Modifier.padding(innerPadding))
            is AgendaUiState.Error -> ErrorContent(
                message = state.message,
                onRetry = viewModel::load,
                modifier = Modifier.padding(innerPadding)
            )
            is AgendaUiState.Success -> {
                if (state.events.isEmpty()) {
                    ErrorContent(
                        message = stringResource(R.string.agenda_empty_message),
                        modifier = Modifier.padding(innerPadding)
                    )
                } else {
                    LazyColumn(
                        contentPadding = innerPadding,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        items(state.events) { event ->
                            AgendaEventCard(event = event)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AgendaEventCard(event: AgendaEvent) {
    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = event.name, style = MaterialTheme.typography.titleSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = dateFormat.format(Date(event.startDate)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = dateFormat.format(Date(event.endDate)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (event.rooms.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.agenda_event_room, event.rooms.joinToString()),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (event.teachers.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.agenda_event_teachers, event.teachers.joinToString()),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
