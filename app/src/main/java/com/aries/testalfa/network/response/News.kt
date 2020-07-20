package com.aries.testalfa.network.response

import com.google.gson.annotations.SerializedName

data class News(
        @SerializedName("status") val  status: String,
        @SerializedName("totalResults") val totalResults : String,
        @SerializedName("articles") val articles: ArrayList<Articles>
)