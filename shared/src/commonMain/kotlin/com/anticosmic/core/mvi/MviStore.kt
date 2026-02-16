package com.anticosmic.core.mvi

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * MVI Store contract.
 * Manages State and Effects based on Intents.
 */
interface MviStore<I : MviIntent, S : MviState, E : MviEffect> {
    val state: StateFlow<S>
    val effects: SharedFlow<E>
    fun processIntent(intent: I)
}
