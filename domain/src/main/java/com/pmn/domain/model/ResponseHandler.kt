package com.pmn.domain.model

data class ResponseHandler<out T>(
    val code: Int? = null,
    val data: T? = null,
    val message: String? = null
)