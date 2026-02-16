package com.anticosmic.presentation.planetdetail

import com.anticosmic.core.mvi.MviStore
import com.anticosmic.domain.usecase.GetPlanetDetailsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlanetDetailStore(
    private val coroutineScope: CoroutineScope,
    private val getPlanetDetailsUseCase: GetPlanetDetailsUseCase
) : MviStore<PlanetDetailIntent, PlanetDetailState, PlanetDetailEffect> {

    private val _state = MutableStateFlow(PlanetDetailState())
    override val state: StateFlow<PlanetDetailState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<PlanetDetailEffect>()
    override val effects: SharedFlow<PlanetDetailEffect> = _effects.asSharedFlow()

    override fun processIntent(intent: PlanetDetailIntent) {
        when (intent) {
            is PlanetDetailIntent.LoadPlanetDetails -> loadPlanetDetails(intent.planetId)
            is PlanetDetailIntent.GoBack -> goBack()
        }
    }

    private fun loadPlanetDetails(planetId: String) {
        coroutineScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val planet = getPlanetDetailsUseCase(planetId)
                if (planet != null) {
                    _state.value = _state.value.copy(isLoading = false, planet = planet)
                } else {
                    _state.value = _state.value.copy(isLoading = false, error = "Planet not found")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message ?: "Unknown error")
            }
        }
    }

    private fun goBack() {
        coroutineScope.launch {
            _effects.emit(PlanetDetailEffect.NavigateBack)
        }
    }
}
