package com.neoutils.chatp2p

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.neoutils.chatp2p.home.HomeRoute
import com.neoutils.chatp2p.home.homeModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() = KoinApplication(
    application = {
        modules(homeModule)
    }
) {
    MaterialTheme {
        HomeRoute()
    }
}