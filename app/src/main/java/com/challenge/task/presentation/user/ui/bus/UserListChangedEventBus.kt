package com.challenge.task.presentation.user.ui.bus

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserListChangedEventBus @Inject constructor() {

    private val _bus = MutableStateFlow<UserListChangedEvent?>(null)
    val bus: Flow<UserListChangedEvent> = _bus.filterNotNull()

    fun notifyUserListChanged() {
        _bus.tryEmit(UserListChangedEvent())
    }

    class UserListChangedEvent
}