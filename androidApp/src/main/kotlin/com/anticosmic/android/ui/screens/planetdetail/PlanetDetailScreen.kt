package com.anticosmic.android.ui.screens.planetdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.anticosmic.android.ui.components.CosmicBackground
import com.anticosmic.android.ui.theme.White
import com.anticosmic.presentation.planetdetail.PlanetDetailEffect
import com.anticosmic.presentation.planetdetail.PlanetDetailIntent
import com.anticosmic.presentation.planetdetail.PlanetDetailStore

@Composable
fun PlanetDetailScreen(
    store: PlanetDetailStore,
    planetId: String,
    onBack: () -> Unit
) {
    val state by store.state.collectAsState()

    LaunchedEffect(planetId) {
        store.processIntent(PlanetDetailIntent.LoadPlanetDetails(planetId))
        store.effects.collect { effect ->
            when (effect) {
                is PlanetDetailEffect.NavigateBack -> onBack()
            }
        }
    }

    CosmicBackground {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = White)
            }
        } else {
            val planet = state.planet
            if (planet != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header with Back Button
                    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        IconButton(onClick = { store.processIntent(PlanetDetailIntent.GoBack) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = White
                            )
                        }
                    }

                    // Content
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = planet.name,
                            style = MaterialTheme.typography.headlineLarge,
                            color = White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = planet.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = White.copy(alpha = 0.9f)
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        DetailRow(label = "Distance", value = planet.distanceFromSun)
                        DetailRow(label = "Gravity", value = planet.gravity)
                        DetailRow(label = "Temperature", value = planet.temperature)
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Planet not found", color = White)
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = White.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = White
        )
    }
}
