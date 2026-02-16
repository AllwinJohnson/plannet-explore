package com.anticosmic.presentation.planetlist

import com.anticosmic.core.mvi.MviStore
import com.anticosmic.domain.usecase.GetPlanetsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlanetListStore(
    private val coroutineScope: CoroutineScope,
    private val getPlanetsUseCase: GetPlanetsUseCase
) : MviStore<PlanetListIntent, PlanetListState, PlanetListEffect> {

    private val _state = MutableStateFlow(PlanetListState())
    override val state: StateFlow<PlanetListState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<PlanetListEffect>()
    override val effects: SharedFlow<PlanetListEffect> = _effects.asSharedFlow()

    init {
        processIntent(PlanetListIntent.LoadPlanets)
    }

    override fun processIntent(intent: PlanetListIntent) {
        when (intent) {
            is PlanetListIntent.LoadPlanets -> loadPlanets()
            is PlanetListIntent.OnPlanetSelected -> onPlanetSelected(intent.planetId)
        }
    }

    private fun loadPlanets() {
        coroutineScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val planets = getPlanetsUseCase()
                _state.value = _state.value.copy(isLoading = false, planets = planets)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error")
            }
        }
    }

    private fun onPlanetSelected(planetId: String) {
        coroutineScope.launch {
            _effects.emit(PlanetListEffect.NavigateToDetails(planetId))
        }
    }
}
