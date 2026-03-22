package com.example.myges.ui.grades

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myges.R
import com.example.myges.core.domain.model.CourseGrade
import com.example.myges.core.domain.model.Semester
import com.example.myges.ui.components.EmptyContent
import com.example.myges.ui.components.ErrorContent
import com.example.myges.ui.components.LoadingContent
import com.example.myges.ui.theme.extendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradesScreen(viewModel: GradesViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(R.string.nav_grades),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when (val state = uiState) {
            is GradesUiState.Loading -> LoadingContent(modifier = Modifier.padding(innerPadding))
            is GradesUiState.Error -> ErrorContent(
                message = state.message,
                onRetry = viewModel::load,
                modifier = Modifier.padding(innerPadding)
            )
            is GradesUiState.Success -> {
                if (state.data.semesters.isEmpty()) {
                    EmptyContent(
                        message = stringResource(R.string.grades_empty_message),
                        modifier = Modifier.padding(innerPadding)
                    )
                } else {
                    LazyColumn(
                        contentPadding = innerPadding,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        item { Spacer(Modifier.height(4.dp)) }
                        items(state.data.semesters, key = { it.name }) { semester ->
                            SemesterCard(semester = semester)
                        }
                        item { Spacer(Modifier.height(8.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun SemesterCard(semester: Semester) {
    var expanded by rememberSaveable(semester.name) { mutableStateOf(false) }
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(durationMillis = 250),
        label = "chevron_${semester.name}"
    )

    // Summary: average of all courses that have an average
    val semesterAvg = semester.courses
        .mapNotNull { it.average }
        .takeIf { it.isNotEmpty() }
        ?.average()

    val gradePass = MaterialTheme.extendedColors.gradePass
    val gradeFail = MaterialTheme.colorScheme.error
    fun gradeColor(value: Double?) =
        if (value != null && value >= 10.0) gradePass else gradeFail

    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // ── Header (always visible, clickable) ──────────────────────────
            Surface(
                onClick = { expanded = !expanded },
                shape = if (expanded) RoundedCornerShape(
                    topStart = 20.dp, topEnd = 20.dp,
                    bottomStart = 0.dp, bottomEnd = 0.dp
                ) else MaterialTheme.shapes.large,
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = semester.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${semester.courses.size} cadeira${if (semester.courses.size != 1) "s" else ""}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Semester average badge
                        semesterAvg?.let { avg ->
                            val color = gradeColor(avg)
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                color = color.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "%.1f".format(avg),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    ),
                                    color = color
                                )
                            }
                        }

                        // Animated chevron
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = if (expanded) "Recolher" else "Expandir",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(chevronRotation)
                        )
                    }
                }
            }

            // ── Expandable content ──────────────────────────────────────────
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    animationSpec = tween(250),
                    expandFrom = Alignment.Top
                ) + fadeIn(animationSpec = tween(200)),
                exit = shrinkVertically(
                    animationSpec = tween(250),
                    shrinkTowards = Alignment.Top
                ) + fadeOut(animationSpec = tween(150))
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(Modifier.height(12.dp))
                    semester.courses.forEachIndexed { index, course ->
                        CourseRow(course = course)
                        if (index < semester.courses.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 10.dp),
                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                            )
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                }
            }
        }
    }
}

@Composable
private fun CourseRow(course: CourseGrade) {
    val gradePass = MaterialTheme.extendedColors.gradePass
    val gradeFail = MaterialTheme.colorScheme.error

    fun gradeColor(value: Double?): Color =
        if (value != null && value >= 10.0) gradePass else gradeFail

    Column(modifier = Modifier.padding(vertical = 2.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = course.name.ifBlank { stringResource(R.string.grades_unnamed) },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                course.average?.let { avg ->
                    val color = gradeColor(avg)
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = color.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "%.1f".format(avg),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            ),
                            color = color
                        )
                    }
                }
                course.letterMark?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        course.average?.let { avg ->
            val progress = (avg / 20.0).toFloat().coerceIn(0f, 1f)
            val color = gradeColor(avg)
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(CircleShape),
                color = color,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        val metaItems = buildList<String> {
            course.ccAverage?.let { add(stringResource(R.string.grades_cc, it)) }
            course.exam?.let { add(stringResource(R.string.grades_exam, it)) }
            if (course.absences > 0) add(stringResource(R.string.grades_absences, course.absences))
        }

        if (metaItems.isNotEmpty()) {
            Spacer(Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                metaItems.forEachIndexed { index, text ->
                    val isAbsences = course.absences > 0 && index == metaItems.lastIndex
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isAbsences) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        if (course.ccGrades.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                course.ccGrades.forEach { grade ->
                    val chipColor = gradeColor(grade)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(chipColor.copy(alpha = 0.12f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "%.1f".format(grade),
                            style = MaterialTheme.typography.labelSmall,
                            color = chipColor
                        )
                    }
                }
            }
        }
    }
}
