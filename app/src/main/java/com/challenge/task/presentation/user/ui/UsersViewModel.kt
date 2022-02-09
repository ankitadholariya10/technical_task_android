package com.challenge.task.presentation.user.ui

import androidx.lifecycle.viewModelScope
import com.github.ajalt.timberkt.Timber
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import com.challenge.task.presentation.user.domain.UserInteractor
import com.challenge.task.presentation.user.domain.entity.User
import com.challenge.task.presentation.user.ui.bus.UserListChangedEventBus
import com.challenge.task.presentation.user.ui.entity.UserUiEntity
import com.challenge.task.utils.BaseViewModel
import com.challenge.task.utils.Event
import com.challenge.task.utils.formatPassedTime
import com.challenge.task.utils.showToast
import com.challenge.task.utils.tickerFlow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModel @Inject constructor(
    private val interactor: UserInteractor,
    private val userListChangedEventBus: UserListChangedEventBus
) : BaseViewModel() {

    // TODO show message if users if empty
    val users = userListChangedEventBus.bus
        .onStart { emit(UserListChangedEventBus.UserListChangedEvent()) }
        .map { interactor.getUsers() }
        .flatMapLatest { users ->
            tickerFlow(interval = USER_LIST_UPDATE_INTERVAL_MS, initialDelay = 0) { users }
        }
        .map { users -> users.map { mapToUiEntity(it) } }
        .catch {
            // TODO improve error handling
            Timber.e(it) { "Error while loading users" }
            _isUsersLoading.value = false
            _isErrorOccurred.value = true
        }
        .onEach {
            _isUsersLoading.value = false
            _isErrorOccurred.value = false
        }

    private val _isUsersLoading = MutableStateFlow(true)
    val isUsersLoading: StateFlow<Boolean> = _isUsersLoading

    private val _isErrorOccurred = MutableStateFlow(false)
    val isErrorStateVisible: StateFlow<Boolean> = _isErrorOccurred

    private val _showUserDeletingConfirmation = MutableStateFlow<Event<DeleteUserRequest>?>(null)
    val showUserDeletingConfirmation: Flow<Event<DeleteUserRequest>> = _showUserDeletingConfirmation
        .filterNotNull()

    private var userIdToDelete: Long? = null

    fun onUserLongClicked(userId: Long) {
        userIdToDelete = userId
        _showUserDeletingConfirmation.tryEmit(Event(DeleteUserRequest()))
    }

    fun onUserDeletingConfirmed() {
        userIdToDelete?.let { userId ->
            viewModelScope.launch(createErrorHandler()) {
                interactor.deleteUser(userId)
                showToast("User has been deleted")
                userListChangedEventBus.notifyUserListChanged()
            }
        }
    }

    private fun mapToUiEntity(it: User): UserUiEntity {
        return UserUiEntity(it.id, it.name, it.email, formatPassedTime(it.createdAt))
    }

    // TODO extract stings to resources
    private fun createErrorHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, err ->
            Timber.e(err) { "Error occurred while deleting user" }
            showToast("Something went wrong")
        }
    }

    class DeleteUserRequest

    companion object {
        private const val USER_LIST_UPDATE_INTERVAL_MS = 5000L
    }
}