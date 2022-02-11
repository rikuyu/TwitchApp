package com.example.twitchapp.util

sealed class Status<out T> {
    object Loading : Status<Nothing>()
    class Success<T>(val data: T) : Status<T>()
    class Error(val throwable: Throwable) : Status<Nothing>()
}