package com.anticosmic.presentation.explore

import com.anticosmic.core.mvi.MviEffect
import com.anticosmic.core.mvi.MviIntent
import com.anticosmic.core.mvi.MviState

sealed interface ExploreIntent : MviIntent {
    data object StartExploration : ExploreIntent
}

data class ExploreState(
    val isLoading: Boolean = false
) : MviState

sealed interface ExploreEffect : MviEffect {
    data object NavigateToPlanetList : ExploreEffect
}
