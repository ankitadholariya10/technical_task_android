

package com.challenge.task.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Flow<T>.observe(owner: LifecycleOwner, block: (T) -> Unit) {
    owner.lifecycleScope.launch {
        collect { block.invoke(it) }
    }
}

fun <T> Flow<Event<T>>.observeEvent(owner: LifecycleOwner, block: (T) -> Unit) {
    owner.lifecycleScope.launch {
        collect { event ->
            event.consume()?.let { toast ->
                block.invoke(toast)
            }
        }
    }
}

