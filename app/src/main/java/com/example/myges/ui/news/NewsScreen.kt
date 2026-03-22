package com.example.myges.ui.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myges.R
import com.example.myges.core.domain.model.NewsItem
import com.example.myges.ui.components.EmptyContent
import com.example.myges.ui.components.ErrorContent
import com.example.myges.ui.components.LoadingContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = stringResource(R.string.nav_news),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        when (val state = uiState) {
            is NewsUiState.Loading -> LoadingContent(modifier = Modifier.padding(innerPadding))
            is NewsUiState.Error -> ErrorContent(
                message = state.message,
                onRetry = viewModel::load,
                modifier = Modifier.padding(innerPadding)
            )
            is NewsUiState.Success -> {
                if (state.items.isEmpty()) {
                    EmptyContent(
                        message = stringResource(R.string.news_empty_message),
                        modifier = Modifier.padding(innerPadding)
                    )
                } else {
                    LazyColumn(
                        contentPadding = innerPadding,
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        item { Spacer(Modifier.height(4.dp)) }
                        items(state.items, key = { it.title }) { item ->
                            NewsCard(item = item)
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                            )
                        }
                        item { Spacer(Modifier.height(8.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsCard(item: NewsItem) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Meta line: author · date
            if (item.authorName != null || item.date != null) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    val meta = listOfNotNull(item.authorName, item.date).joinToString(" · ")
                    Text(
                        text = meta,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (!item.text.isNullOrBlank()) {
                Text(
                    text = item.text,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
