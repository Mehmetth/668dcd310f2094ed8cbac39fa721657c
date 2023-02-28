package com.mehmetpetek.satellitedemo.data

sealed class Resource<T>(val data: T? = null, val message: Throwable? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: Throwable? = null) : Resource<T>(null, message)
}