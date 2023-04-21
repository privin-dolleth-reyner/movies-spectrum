package com.privin.movies

import java.io.IOException

sealed class ApiError: IOException() {
    data class UnAuthorized(val msg: String): ApiError()
    data class PageNotFound(val msg: String): ApiError()
    data class InternalError(val msg: String): ApiError()
}