package com.anticosmic.android.ui.screens.planetlist

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanetListScreen(
    store: PlanetListStore,
    onPlanetSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    val state by store.state.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = state.selectedPageIndex,
        pageCount = { state.planets.size }
    )

    // Sync pager index back to store
    LaunchedEffect(pagerState.currentPage) {
        store.processIntent(PlanetListIntent.UpdatePageIndex(pagerState.currentPage))
    }

    LaunchedEffect(Unit) {
        store.effects.collect { effect ->
            when (effect) {
                is PlanetListEffect.NavigateToDetails -> onPlanetSelected(effect.planetId)
            }
        }
    }

    // Handle system back button to go back to Explore
    BackHandler {
        onBack()
    }

    CosmicBackground {
        Column(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    HorizontalPager(
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = 64.dp),
                        pageSpacing = 16.dp,
                        userScrollEnabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp)
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
                                // Direct intent processing for better responsiveness
                                store.processIntent(PlanetListIntent.OnPlanetSelected(planet.id))
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Page Indicator
                    Row(
                        modifier = Modifier
                            .height(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(state.planets.size) { iteration ->
                            val color = if (pagerState.currentPage == iteration) White else White.copy(alpha = 0.5f)
                            val width = if (pagerState.currentPage == iteration) 24.dp else 8.dp
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(width = width, height = 8.dp)
                            )
                        }
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
    // Smooth interpolation based on offset
    val scale = 1f - (pageOffset.coerceIn(0f, 1f) * 0.15f)
    val alpha = 1f - (pageOffset.coerceIn(0f, 1f) * 0.5f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
                shadowElevation = 12.dp.toPx()
                shape = Shapes.large // 24dp from theme
                clip = true
            }
            .clip(Shapes.large)
            .background(Color.White) // Card base
            .clickable(
                indication = null, // Remove ripple to avoid interference with gestures
                interactionSource = remember { MutableInteractionSource() }
            ) { 
                onClick() 
            }
    ) {
        // Background Gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(CardGradientTop, CardGradientBottom)
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Large Planet Image Placeholder with Glow
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(White.copy(alpha = 0.3f), White.copy(alpha = 0.05f))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Inner circle for "planet" look
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                        .clip(CircleShape)
                        .background(White.copy(alpha = 0.15f))
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = planet.name,
                style = MaterialTheme.typography.headlineMedium,
                color = White,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = planet.description,
                style = MaterialTheme.typography.bodyMedium,
                color = White.copy(alpha = 0.85f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}
