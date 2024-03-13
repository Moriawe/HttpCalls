package com.moriawe.ktorapp.data.remote

import com.moriawe.ktorapp.data.remote.dto.PostRequest
import com.moriawe.ktorapp.data.remote.dto.PostResponse

interface PostService {

    suspend fun getAllPosts(): Resource<List<PostResponse>>

    suspend fun createNewPost(postRequest: PostRequest): Resource<PostResponse>?

    suspend fun updatePost(postResponse: PostResponse): Resource<PostResponse>?

    suspend fun deletePost(id: Int): Resource<Unit>

}