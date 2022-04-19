package com.mgijon.data.model

import com.google.gson.annotations.SerializedName

data class CharacterDataWrapper(
    @SerializedName("code") val code : Int? = null,
    @SerializedName("status") val status : String? = null,
    @SerializedName("copyright") val copyright : String? = null,
    @SerializedName("attributionText") val attributionText : String? = null,
    @SerializedName("attributionHTML") val attributionHTML : String? = null,
    @SerializedName("data") val data : CharacterDataContainer? = null,
    @SerializedName("etag") val etag : String? = null,
)