package com.neoutils.chatp2p

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform