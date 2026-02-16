package com.anticosmic.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.ui.Modifier
import com.anticosmic.android.ui.screens.explore.ExploreScreen
import com.anticosmic.android.ui.screens.planetdetail.PlanetDetailScreen
import com.anticosmic.android.ui.screens.planetlist.PlanetListScreen
import com.anticosmic.android.ui.theme.AntiCosmicTheme
import com.anticosmic.data.datasource.LocalPlanetDataSource
import com.anticosmic.data.repository.PlanetRepositoryImpl
import com.anticosmic.domain.usecase.GetPlanetDetailsUseCase
import com.anticosmic.domain.usecase.GetPlanetsUseCase
import com.anticosmic.presentation.explore.ExploreStore
import com.anticosmic.presentation.planetdetail.PlanetDetailStore
import com.anticosmic.presentation.planetlist.PlanetListStore

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // simple DI (Manual for now)
        val dataSource = LocalPlanetDataSource()
        val repository = PlanetRepositoryImpl(dataSource)
        val getPlanetsUseCase = GetPlanetsUseCase(repository)
        val getPlanetDetailsUseCase = GetPlanetDetailsUseCase(repository)

        enableEdgeToEdge()
        
        setContent {
            AntiCosmicTheme {
                AppContent(
                    getPlanetsUseCase = getPlanetsUseCase,
                    getPlanetDetailsUseCase = getPlanetDetailsUseCase
                )
            }
        }
    }
}

enum class Screen {
    Explore, PlanetList, PlanetDetail
}

@Composable
fun AppContent(
    getPlanetsUseCase: GetPlanetsUseCase,
    getPlanetDetailsUseCase: GetPlanetDetailsUseCase
) {
    var currentScreen by remember { mutableStateOf(Screen.Explore) }
    var selectedPlanetId by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Scoped Stores
    // In a real app, these should be ViewModel scoped, but for this exercise, remember is acceptable if config changes aren't a primary concern yet (or we handle them via VM later)
    val exploreStore = remember { ExploreStore(coroutineScope) }
    val planetListStore = remember { PlanetListStore(coroutineScope, getPlanetsUseCase) }
    val planetDetailStore = remember { PlanetDetailStore(coroutineScope, getPlanetDetailsUseCase) }

    Box(modifier = Modifier.fillMaxSize()) {
    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            if (targetState > initialState) {
                (slideInHorizontally { it } + fadeIn()) togetherWith 
                (slideOutHorizontally { -it } + fadeOut())
            } else {
                (slideInHorizontally { -it } + fadeIn()) togetherWith 
                (slideOutHorizontally { it } + fadeOut())
            }.using(
                SizeTransform(clip = false)
            )
        },
        label = "screen_transition"
    ) { screen ->
        when (screen) {
            Screen.Explore -> {
                ExploreScreen(
                    store = exploreStore,
                    onNavigateToPlanetList = { currentScreen = Screen.PlanetList }
                )
            }
            Screen.PlanetList -> {
                PlanetListScreen(
                    store = planetListStore,
                    onPlanetSelected = { id ->
                        selectedPlanetId = id
                        currentScreen = Screen.PlanetDetail
                    }
                )
            }
            Screen.PlanetDetail -> {
                val planetId = selectedPlanetId
                if (planetId != null) {
                    PlanetDetailScreen(
                        store = planetDetailStore,
                        planetId = planetId,
                        onBack = { currentScreen = Screen.PlanetList }
                    )
                }
            }
        }
    }
    }
}
