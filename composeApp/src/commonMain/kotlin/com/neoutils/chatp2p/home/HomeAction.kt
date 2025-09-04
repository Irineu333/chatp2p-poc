package com.neoutils.chatp2p.home

sealed class HomeAction {
    data object OnSend : HomeAction()
    data class OnChange(val message: String) : HomeAction()
}