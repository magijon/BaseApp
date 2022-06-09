package com.mgijon.domain.model

import com.google.gson.annotations.SerializedName

data class CharacterModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("modified") val modified: String? = null,
    @SerializedName("resourceURI") val resourceURI: String? = null,
    @SerializedName("urls") val urls: List<Url>? = null,
    @SerializedName("thumbnail") val thumbnail: Image? = null
)

