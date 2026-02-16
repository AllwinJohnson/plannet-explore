package com.anticosmic.android.ui.screens.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anticosmic.android.ui.components.CosmicBackground
import com.anticosmic.android.ui.theme.White
import com.anticosmic.presentation.explore.ExploreEffect
import com.anticosmic.presentation.explore.ExploreIntent
import com.anticosmic.presentation.explore.ExploreStore

@Composable
fun ExploreScreen(
    store: ExploreStore,
    onNavigateToPlanetList: () -> Unit
) {
    val state by store.state.collectAsState()

    LaunchedEffect(Unit) {
        store.processIntent(ExploreIntent.StartExploration)
        store.effects.collect { effect ->
            when (effect) {
                is ExploreEffect.NavigateToPlanetList -> onNavigateToPlanetList()
            }
        }
    }

    CosmicBackground {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Anti-Cosmic",
                    style = MaterialTheme.typography.headlineLarge,
                    color = White
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (state.isLoading) {
                    CircularProgressIndicator(color = White)
                } else {
                    Text(
                        text = "Explore the Universe",
                        style = MaterialTheme.typography.titleMedium,
                        color = White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}
