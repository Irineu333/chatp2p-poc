package com.neoutils.chatp2p

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "chatp2p",
    ) {
        App()
    }
}