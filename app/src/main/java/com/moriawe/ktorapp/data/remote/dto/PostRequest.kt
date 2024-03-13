package com.moriawe.ktorapp.data.remote.dto

import kotlinx.serialization.Serializable

// Has to look like the data structure in the api

@Serializable
data class PostRequest(
    val userId: Int,
    val body: String,
    val title: String
)

