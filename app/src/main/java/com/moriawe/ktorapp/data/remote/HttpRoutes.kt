package com.moriawe.ktorapp.data.remote

object HttpRoutes {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    const val GET = "$BASE_URL/posts"
    const val POST = "$BASE_URL/posts/1"
    const val PUT = "$BASE_URL/1"
    const val DELETE = "$BASE_URL/1"

}