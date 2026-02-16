package com.anticosmic.presentation.explore

import com.anticosmic.core.mvi.MviStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExploreStore(
    private val coroutineScope: CoroutineScope
) : MviStore<ExploreIntent, ExploreState, ExploreEffect> {

    private val _state = MutableStateFlow(ExploreState())
    override val state: StateFlow<ExploreState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<ExploreEffect>()
    override val effects: SharedFlow<ExploreEffect> = _effects.asSharedFlow()

    override fun processIntent(intent: ExploreIntent) {
        when (intent) {
            is ExploreIntent.StartExploration -> startExploration()
        }
    }

    private fun startExploration() {
        coroutineScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            delay(2000) // Simulate loading / Splash delay
            _state.value = _state.value.copy(isLoading = false)
            _effects.emit(ExploreEffect.NavigateToPlanetList)
        }
    }
}
