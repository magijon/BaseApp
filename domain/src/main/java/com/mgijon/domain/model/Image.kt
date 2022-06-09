package com.mgijon.domain.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("path") val path: String? = null,
    @SerializedName("extension") val extension: String? = null
)
