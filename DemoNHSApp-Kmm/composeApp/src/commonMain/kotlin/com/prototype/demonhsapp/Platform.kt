package com.prototype.demonhsapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform