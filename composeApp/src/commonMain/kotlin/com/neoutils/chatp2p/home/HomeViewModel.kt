package com.neoutils.chatp2p.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class HomeViewModel(
    private val sendMessage: SendMessage
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnChange -> {
                _uiState.update {
                    it.copy(message = action.message)
                }
            }

            HomeAction.OnSend -> {

                sendMessage(uiState.value.message)

                _uiState.update {
                    it.copy(
                        message = "",
                        messages = it.messages + Message(
                            author = "Me",
                            body = uiState.value.message,
                            myself = Random.nextBoolean(),
                        )
                    )
                }
            }
        }
    }
}
