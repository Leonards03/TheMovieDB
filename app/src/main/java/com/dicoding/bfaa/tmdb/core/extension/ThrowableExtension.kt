package com.dicoding.bfaa.tmdb.core.extension

fun Throwable.messageOrToString() =
    this.message ?: this.toString()