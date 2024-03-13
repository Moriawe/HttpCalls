package com.moriawe.ktorapp.data.remote

import com.moriawe.ktorapp.data.remote.ApiClient.client
import com.moriawe.ktorapp.data.remote.dto.PostRequest
import com.moriawe.ktorapp.data.remote.dto.PostResponse
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


class PostServiceImpl(
    private val baseRepository: BaseRepository
): PostService {

    override suspend fun getAllPosts(): Resource<List<PostResponse>> {

        return baseRepository.handleApiCall {
            client.get(HttpRoutes.GET).body()
        }
    }

    override suspend fun createNewPost(postRequest: PostRequest): Resource<PostResponse> {
        return baseRepository.handleApiCall {
            client.post(HttpRoutes.GET) {
                contentType(ContentType.Application.Json)
                setBody(postRequest)
            }.body()
        }
    }

    override suspend fun updatePost(postResponse: PostResponse): Resource<PostResponse> {
        return baseRepository.handleApiCall {
            client.put(HttpRoutes.POST) {
                setBody(postResponse)
            }.body()
        }
    }

    override suspend fun deletePost(id: Int): Resource<Unit> {
        return baseRepository.handleApiCall {
            client.delete(HttpRoutes.POST)
        }
    }
}