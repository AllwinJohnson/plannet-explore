package com.anticosmic.presentation.planetdetail

import com.anticosmic.core.mvi.MviEffect
import com.anticosmic.core.mvi.MviIntent
import com.anticosmic.core.mvi.MviState
import com.anticosmic.domain.model.Planet

sealed interface PlanetDetailIntent : MviIntent {
    data class LoadPlanetDetails(val planetId: String) : PlanetDetailIntent
    data object GoBack : PlanetDetailIntent
}

data class PlanetDetailState(
    val isLoading: Boolean = false,
    val planet: Planet? = null,
    val error: String? = null
) : MviState

sealed interface PlanetDetailEffect : MviEffect {
    data object NavigateBack : PlanetDetailEffect
}
