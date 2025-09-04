package com.neoutils.chatp2p

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.neoutils.chatp2p.home.HomeRoute
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() = MaterialTheme {
    HomeRoute()
}
