package com.neoutils.chatp2p.home

data class HomeUiState(
    val messages: List<Message> = emptyList(),
    val message: String = ""
)