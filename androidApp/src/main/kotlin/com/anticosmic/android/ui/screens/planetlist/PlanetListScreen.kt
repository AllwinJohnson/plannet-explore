package com.anticosmic.android.ui.screens.planetlist

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import com.anticosmic.android.ui.components.CosmicBackground
import com.anticosmic.android.ui.theme.CardGradientBottom
import com.anticosmic.android.ui.theme.CardGradientTop
import com.anticosmic.android.ui.theme.Shapes
import com.anticosmic.android.ui.theme.White
import com.anticosmic.domain.model.Planet
import com.anticosmic.presentation.planetlist.PlanetListEffect
import com.anticosmic.presentation.planetlist.PlanetListIntent
import com.anticosmic.presentation.planetlist.PlanetListStore

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanetListScreen(
    store: PlanetListStore,
    onPlanetSelected: (String) -> Unit
) {
    val state by store.state.collectAsState()
    val pagerState = rememberPagerState(pageCount = { state.planets.size })

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
                text = "Explore The Universe",
                style = MaterialTheme.typography.headlineLarge,
                color = White,
                modifier = Modifier
                    .padding(top = 48.dp, start = 24.dp, end = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = White)
                }
            } else {
                    HorizontalPager(
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = 64.dp),
                        pageSpacing = 16.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    ) { page ->
                        val planet = state.planets[page]
                        val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                        ).absoluteValue

                        PlanetItem(
                            planet = planet,
                            pageOffset = pageOffset,
                            onClick = {
                                store.processIntent(PlanetListIntent.OnPlanetSelected(planet.id))
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Page Indicator
                    Row(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(state.planets.size) { iteration ->
                            val color = if (pagerState.currentPage == iteration) White else White.copy(alpha = 0.5f)
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(8.dp)
                            )
                        }
                    }
            }
        }
    }
}

@Composable
fun PlanetItem(
    planet: Planet,
    pageOffset: Float,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (pageOffset == 0f) 1f else 0.85f,
        animationSpec = tween(durationMillis = 300),
        label = "scale"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (pageOffset == 0f) 1f else 0.5f,
        animationSpec = tween(durationMillis = 300),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .clip(Shapes.large)
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
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f)
                    .clip(Shapes.extraLarge) // Circle-ish
                    .background(White.copy(alpha = 0.2f))
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = planet.name,
                style = MaterialTheme.typography.displaySmall,
                color = White
            )
            Text(
                text = planet.description.take(50) + "...",
                style = MaterialTheme.typography.bodySmall,
                color = White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
