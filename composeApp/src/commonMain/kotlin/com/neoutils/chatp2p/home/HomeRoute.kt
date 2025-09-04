package com.neoutils.chatp2p.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = viewModel {
        HomeViewModel(sendMessage = SendMessage())
    }
) {

    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
    )
}