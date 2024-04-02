package com.pmn.data.remote.model

import com.pmn.domain.model.SomeEntityDomain
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SomeEntityRemote(
    val id: Long,
    val text: String,
    val description: String? = null
)

fun SomeEntityRemote.toDomain() = SomeEntityDomain(
    id = id,
    text = text,
    description = description
)
