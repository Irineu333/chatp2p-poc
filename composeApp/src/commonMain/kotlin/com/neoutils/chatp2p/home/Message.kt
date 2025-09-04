package com.neoutils.chatp2p.home

data class Message(
    val myself: Boolean,
    val author: String,
    val body: String
)