package com.neoutils.chatp2p.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel(
    private val chatManager: ChatManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            chatManager.onMessage.collect { message ->
                _uiState.update {
                    it.copy(
                        messages = it.messages + message
                    )
                }
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnChange -> {
                _uiState.update {
                    it.copy(message = action.message)
                }
            }

            HomeAction.OnSend -> {

                chatManager.sendMessage(uiState.value.message)

                _uiState.update {
                    it.copy(
                        message = "",
                        messages = it.messages + Message(
                            author = "Me",
                            body = uiState.value.message,
                            myself = true,
                        )
                    )
                }
            }
        }
    }
}
