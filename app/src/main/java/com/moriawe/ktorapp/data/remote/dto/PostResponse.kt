package com.moriawe.ktorapp.data.remote.dto

import kotlinx.serialization.Serializable

// Has to look like the data structure in the api

@Serializable
data class PostResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

// If you name yours variables to something else than in the Json, do like this
/*
@Serializable
data class Post (
    @SerialName( value = "id")
    val id:Int,

    @SerialName( value = "title")
    val bookTitle:String,

    @SerialName( value = "body")
    val summary: String,

    @SerialName( value = "userId")
    val userId:String
)
*/
