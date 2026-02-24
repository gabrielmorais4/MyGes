package com.example.myges.ui.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.example.myges.core.domain.model.NewsItem
import com.example.myges.ui.components.ErrorContent
import com.example.myges.ui.components.LoadingContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.nav_news)) })
        }
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
                    ErrorContent(
                        message = stringResource(R.string.news_empty_message),
                        modifier = Modifier.padding(innerPadding)
                    )
                } else {
                    LazyColumn(
                        contentPadding = innerPadding,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        items(state.items) { item ->
                            NewsCard(item = item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsCard(item: NewsItem) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = item.title, style = MaterialTheme.typography.titleSmall)
            if (item.authorName != null || item.date != null) {
                Text(
                    text = listOfNotNull(item.authorName, item.date).joinToString(" · "),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (!item.text.isNullOrBlank()) {
                Text(
                    text = item.text,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 5
                )
            }
        }
    }
}
