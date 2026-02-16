package com.anticosmic.android.ui.screens.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anticosmic.android.ui.components.CosmicBackground
import com.anticosmic.android.ui.theme.RocketOrange
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "EXPLORE",
                    style = MaterialTheme.typography.displayLarge,
                    color = White,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 8.sp,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "THE UNIVERSE",
                    style = MaterialTheme.typography.displayMedium,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(64.dp))
                
                if (state.isLoading) {
                    CircularProgressIndicator(color = White)
                } else {
                    Button(
                        onClick = { store.processIntent(ExploreIntent.StartExploration) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RocketOrange,
                            contentColor = White
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(56.dp)
                    ) {
                        Text(
                            text = "GET STARTED",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
            
            // Subtle footer text
            Text(
                text = "Anti-Cosmic v0.2.0",
                color = White.copy(alpha = 0.3f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
        }
    }
}
