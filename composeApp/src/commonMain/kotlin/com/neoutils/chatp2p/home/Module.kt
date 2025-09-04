package com.neoutils.chatp2p.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            chatManager = get()
        )
    }

    single {
        ChatManager(scope = CoroutineScope(Dispatchers.IO))
    }
}