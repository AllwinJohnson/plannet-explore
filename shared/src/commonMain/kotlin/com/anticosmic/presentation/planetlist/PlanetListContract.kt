package com.anticosmic.presentation.planetlist

import com.anticosmic.core.mvi.MviEffect
import com.anticosmic.core.mvi.MviIntent
import com.anticosmic.core.mvi.MviState
import com.anticosmic.domain.model.Planet

sealed interface PlanetListIntent : MviIntent {
    data object LoadPlanets : PlanetListIntent
    data class UpdatePageIndex(val index: Int) : PlanetListIntent
    data class OnPlanetSelected(val planetId: String) : PlanetListIntent
}

data class PlanetListState(
    val isLoading: Boolean = false,
    val planets: List<Planet> = emptyList(),
    val selectedPageIndex: Int = 0,
    val error: String? = null
) : MviState

sealed interface PlanetListEffect : MviEffect {
    data class NavigateToDetails(val planetId: String) : PlanetListEffect
}
