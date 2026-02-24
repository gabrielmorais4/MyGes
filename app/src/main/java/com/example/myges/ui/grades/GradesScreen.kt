package com.example.myges.ui.grades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.example.myges.core.domain.model.CourseGrades
import com.example.myges.core.domain.model.Semester
import com.example.myges.ui.components.ErrorContent
import com.example.myges.ui.components.LoadingContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradesScreen(viewModel: GradesViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.nav_grades)) })
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is GradesUiState.Loading -> LoadingContent(modifier = Modifier.padding(innerPadding))
            is GradesUiState.Error -> ErrorContent(
                message = state.message,
                onRetry = viewModel::load,
                modifier = Modifier.padding(innerPadding)
            )
            is GradesUiState.Success -> {
                LazyColumn(
                    contentPadding = innerPadding,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    items(state.data.semesters) { semester ->
                        SemesterCard(semester = semester)
                    }
                }
            }
        }
    }
}

@Composable
private fun SemesterCard(semester: Semester) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = semester.name, style = MaterialTheme.typography.titleMedium)
                semester.average?.let {
                    Text(
                        text = stringResource(R.string.grades_semester_average, it),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            semester.courses.forEach { course ->
                CourseRow(course = course)
            }
        }
    }
}

@Composable
private fun CourseRow(course: CourseGrades) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = course.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            course.average?.let {
                Text(
                    text = "%.2f".format(it),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        course.grades.forEach { entry ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = entry.name ?: stringResource(R.string.grades_unnamed),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                val gradeText = when {
                    entry.isAbsence -> stringResource(R.string.grades_absence)
                    entry.grade != null -> "%.2f".format(entry.grade)
                    else -> stringResource(R.string.grades_pending)
                }
                Text(
                    text = gradeText,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (entry.isAbsence) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
