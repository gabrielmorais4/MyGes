package com.example.myges.ui.agenda

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myges.R
import com.example.myges.core.domain.model.AgendaEvent
import com.example.myges.ui.components.EmptyContent
import com.example.myges.ui.components.ErrorContent
import com.example.myges.ui.components.LoadingContent
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AgendaScreen(viewModel: AgendaViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val weekOffset by viewModel.weekOffset.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val weekLabel = remember(weekOffset) {
        val monday = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(weekOffset.toLong())
        val sunday = monday.plusDays(6)
        val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.getDefault())
        "${monday.format(formatter)} – ${sunday.format(formatter)}"
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.nav_agenda),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = weekLabel,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = viewModel::previousWeek) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.agenda_previous_week)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = viewModel::nextWeek) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = stringResource(R.string.agenda_next_week)
                        )
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.background
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
                    EmptyContent(
                        message = stringResource(R.string.agenda_empty_message),
                        modifier = Modifier.padding(innerPadding)
                    )
                } else {
                    val todayCal = remember { Calendar.getInstance() }

                    // Group events by (year, dayOfYear) — stable key for day grouping
                    val eventsByDay = remember(state.events) {
                        state.events
                            .sortedBy { it.startDate }
                            .groupBy { event ->
                                Calendar.getInstance().apply {
                                    timeInMillis = event.startDate
                                }.let { Pair(it.get(Calendar.YEAR), it.get(Calendar.DAY_OF_YEAR)) }
                            }
                    }

                    LazyColumn(
                        contentPadding = innerPadding,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        item { Spacer(Modifier.height(4.dp)) }

                        eventsByDay.forEach { (dayKey, events) ->
                            val firstEventDate = Date(events.first().startDate)
                            val isToday = dayKey.first == todayCal.get(Calendar.YEAR) &&
                                    dayKey.second == todayCal.get(Calendar.DAY_OF_YEAR)

                            stickyHeader(key = "${dayKey.first}-${dayKey.second}") {
                                DayHeader(date = firstEventDate, isToday = isToday)
                            }

                            items(events, key = { it.startDate }) { event ->
                                AgendaEventRow(event = event)
                                Spacer(Modifier.height(8.dp))
                            }

                            item { Spacer(Modifier.height(8.dp)) }
                        }

                        item { Spacer(Modifier.height(8.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun DayHeader(date: Date, isToday: Boolean) {
    val dayNumberFormat = remember { SimpleDateFormat("d", Locale.getDefault()) }
    val dayOfWeekFormat = remember { SimpleDateFormat("EEEE", Locale.getDefault()) }
    val monthYearFormat = remember { SimpleDateFormat("MMMM yyyy", Locale.getDefault()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Day number circle — filled if today
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(
                    if (isToday) MaterialTheme.colorScheme.primary
                    else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dayNumberFormat.format(date),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = when {
                    isToday -> MaterialTheme.colorScheme.onPrimary
                    else -> MaterialTheme.colorScheme.onBackground
                },
                textAlign = TextAlign.Center
            )
        }

        // Day name + month/year
        Column {
            Text(
                text = dayOfWeekFormat.format(date)
                    .replaceFirstChar { it.uppercaseChar() },
                style = MaterialTheme.typography.labelLarge,
                color = if (isToday) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = monthYearFormat.format(date)
                    .replaceFirstChar { it.uppercaseChar() },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Trailing divider
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Composable
private fun AgendaEventRow(event: AgendaEvent) {
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val startDate = remember(event.startDate) { Date(event.startDate) }
    val endDate = remember(event.endDate) { Date(event.endDate) }

    val durationMinutes = ((event.endDate - event.startDate) / 60_000).toInt()
    val durationText = buildString {
        if (durationMinutes >= 60) append("${durationMinutes / 60}h")
        if (durationMinutes % 60 > 0) append("${durationMinutes % 60}min")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Time column
        Column(
            modifier = Modifier
                .width(52.dp)
                .padding(top = 14.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = timeFormat.format(startDate),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary
            )
            if (durationText.isNotEmpty()) {
                Text(
                    text = durationText,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Dot connector
        Column(
            modifier = Modifier.padding(top = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }

        // Event card
        Surface(
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Title row with optional type badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.name,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    if (!event.type.isNullOrBlank()) {
                        Spacer(Modifier.width(8.dp))
                        Surface(
                            shape = MaterialTheme.shapes.extraSmall,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = event.type,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                // End time
                Text(
                    text = "até ${timeFormat.format(endDate)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Room + teacher
                if (event.rooms.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MeetingRoom,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = event.rooms.joinToString(", "),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                if (!event.teacher.isNullOrBlank()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = event.teacher,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
