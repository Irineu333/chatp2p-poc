package com.neoutils.chatp2p.home

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            sendMessage = get()
        )
    }

    single {
        SendMessage()
    }
}