package com.minimal.ec135.util

fun Float.round(decimals: Int): Float {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return (kotlin.math.round(this.toDouble() * multiplier) / multiplier).toFloat()
}