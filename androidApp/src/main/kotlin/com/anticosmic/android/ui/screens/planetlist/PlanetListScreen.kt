package com.anticosmic.android.ui.screens.planetlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import com.anticosmic.android.ui.components.CosmicBackground
import com.anticosmic.android.ui.theme.CardGradientBottom
import com.anticosmic.android.ui.theme.CardGradientTop
import com.anticosmic.android.ui.theme.Shapes
import com.anticosmic.android.ui.theme.White
import com.anticosmic.domain.model.Planet
import com.anticosmic.presentation.planetlist.PlanetListEffect
import com.anticosmic.presentation.planetlist.PlanetListIntent
import com.anticosmic.presentation.planetlist.PlanetListStore

@Composable
fun PlanetListScreen(
    store: PlanetListStore,
    onPlanetSelected: (String) -> Unit
) {
    val state by store.state.collectAsState()

    LaunchedEffect(Unit) {
        store.effects.collect { effect ->
            when (effect) {
                is PlanetListEffect.NavigateToDetails -> onPlanetSelected(effect.planetId)
            }
        }
    }

    CosmicBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Solar System",
                style = MaterialTheme.typography.headlineLarge,
                color = White,
                modifier = Modifier.padding(24.dp)
            )

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = White)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.planets) { planet ->
                        PlanetItem(planet = planet, onClick = {
                            store.processIntent(PlanetListIntent.OnPlanetSelected(planet.id))
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun PlanetItem(planet: Planet, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clip(Shapes.medium)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(CardGradientTop, CardGradientBottom)
                )
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Placeholder for Image (Circle)
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(1f)
                    .clip(Shapes.large) // Circle-ish
                    .background(White.copy(alpha = 0.2f))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = planet.name,
                style = MaterialTheme.typography.titleMedium,
                color = White
            )
        }
    }
}
