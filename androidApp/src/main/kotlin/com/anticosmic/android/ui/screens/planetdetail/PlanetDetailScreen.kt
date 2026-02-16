package com.anticosmic.android.ui.screens.planetdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anticosmic.android.ui.components.CosmicBackground
import com.anticosmic.android.ui.theme.CardGradientBottom
import com.anticosmic.android.ui.theme.CardGradientTop
import com.anticosmic.android.ui.theme.Shapes
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
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Hero Section
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                        ) {
                            // Planet Glow
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(350.dp)
                                    .clip(CircleShape)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(White.copy(alpha = 0.2f), Color.Transparent)
                                        )
                                    )
                            )
                            
                            // Planet Placeholder
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(240.dp)
                                    .clip(CircleShape)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(White.copy(alpha = 0.4f), White.copy(alpha = 0.1f))
                                        )
                                    )
                            )
                        }

                        // Content Card (Overlapping)
                        Column(
                            modifier = Modifier
                                .offset(y = (-48).dp)
                                .clip(Shapes.extraLarge)
                                .background(White.copy(alpha = 0.1f))
                                .padding(24.dp)
                        ) {
                            Text(
                                text = planet.name,
                                style = MaterialTheme.typography.displayMedium,
                                color = White,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = planet.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = White.copy(alpha = 0.8f),
                                lineHeight = 24.sp
                            )
                            
                            Spacer(modifier = Modifier.height(32.dp))
                            
                            // Info Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                InfoCard(label = "DISTANCE", value = planet.distanceFromSun)
                                InfoCard(label = "GRAVITY", value = planet.gravity)
                                InfoCard(label = "TEMP", value = planet.temperature)
                            }
                            
                            Spacer(modifier = Modifier.height(32.dp))
                            
                            Text(
                                text = "GALLERY",
                                style = MaterialTheme.typography.labelLarge,
                                color = White,
                                letterSpacing = 2.sp
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Thumbnail Selector
                            LazyRow(
                                contentPadding = PaddingValues(end = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(listOf(1, 2, 3, 4, 5)) {
                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(Shapes.medium)
                                            .background(White.copy(alpha = 0.1f))
                                    )
                                }
                            }
                        }
                    }

                    // Floating Navigation Overlay
                    IconButton(
                        onClick = { store.processIntent(PlanetDetailIntent.GoBack) },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                            .background(White.copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = White
                        )
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Space fragment missing...", color = White)
                }
            }
        }
    }
}

@Composable
fun InfoCard(label: String, value: String) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .clip(Shapes.medium)
            .background(White.copy(alpha = 0.05f))
            .padding(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = White.copy(alpha = 0.5f),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
