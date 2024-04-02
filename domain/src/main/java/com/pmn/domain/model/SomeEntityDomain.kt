package com.pmn.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SomeEntityDomain(
    val id: Long,
    val text: String,
    val description: String? = null
) : Parcelable